package controllers;
//package collections;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import java.util.Base64;
import java.util.Collections;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.hibernate.validator.constraints.NotEmpty;
import api.ReceiptSuggestionResponse;
import java.math.BigDecimal;
import java.util.regex.*;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.io.FileOutputStream;
import java.io.IOException;


import static java.lang.System.out;

@Path("/images")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public class ReceiptImageController {
    //static final String apiKey = "AIzaSyAYdkyGx_Jb9CUVLLJ3sX3_s1Ej_ig3_iI";
    private final AnnotateImageRequest.Builder requestBuilder;

    public ReceiptImageController() {
        // DOCUMENT_TEXT_DETECTION is not the best or only OCR method available
        Feature ocrFeature = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        this.requestBuilder = AnnotateImageRequest.newBuilder().addFeatures(ocrFeature);

    }

    public static int GetOrientation(AnnotateImageResponse res) {
            
           // Calculate the center
            float centerX = 0, centerY = 0;
            for (int i = 0; i < 4; i++) {
                centerX += res.getTextAnnotationsList().get(1).getBoundingPoly().getVertices(i).getX();
                centerY += res.getTextAnnotationsList().get(1).getBoundingPoly().getVertices(i).getY();
            }
            centerX /= 4;
            centerY /= 4;

           int x0 = res.getTextAnnotationsList().get(1).getBoundingPoly().getVertices(0).getX();
            int y0 = res.getTextAnnotationsList().get(1).getBoundingPoly().getVertices(0).getY();

           if (x0 < centerX) {
                if (y0 < centerY) {
                    //       0 -------- 1
                    //       |          |
                    //       3 -------- 2
                    //return EXIF_ORIENTATION_NORMAL; // 1
                    return 1;
                } else {
                    //       1 -------- 2
                    //       |          |
                    //       0 -------- 3
                    //return EXIF_ORIENTATION_270_DEGREE; // 6
                    return 2;
                }
            } else {
                if (y0 < centerY) {
                    //       3 -------- 0
                    //       |          |
                    //       2 -------- 1
                    //return EXIF_ORIENTATION_90_DEGREE; // 8
                    return 3;
                } else {
                    //       2 -------- 3
                    //       |          |
                    //       1 -------- 0
                    //return EXIF_ORIENTATION_180_DEGREE; // 3
                    return 4;
                }
            }
    }

    /**
     * This borrows heavily from the Google Vision API Docs.  See:
     * https://cloud.google.com/vision/docs/detecting-fulltext
     *
     * YOU SHOULD MODIFY THIS METHOD TO RETURN A ReceiptSuggestionResponse:
     *
     * public class ReceiptSuggestionResponse {
     *     String merchantName;
     *     String amount;
     * }
     */
    @POST
    public ReceiptSuggestionResponse parseReceipt(@NotEmpty String base64EncodedImage) throws Exception {
        Image img = Image.newBuilder().setContent(ByteString.copyFrom(Base64.getDecoder().decode(base64EncodedImage))).build();
        AnnotateImageRequest request = this.requestBuilder.setImage(img).build();

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse responses = client.batchAnnotateImages(Collections.singletonList(request));
            AnnotateImageResponse res = responses.getResponses(0);


            String merchantName = null;
            BigDecimal amount = null;
            // Your Algo Here!!
            // Sort text annotations by bounding polygon.  Top-most non-decimal text is the merchant
            // bottom-most decimal text is the total amount

            //Pattern merchantRecognizer = Pattern.compile("^[a-zA-Z][a-zA-Z0-9.,$;_+-]+");
            Pattern amountRecognizer = Pattern.compile("[0-9]*\\.[0-9]{2}");

            Iterator<EntityAnnotation> annotation = res.getTextAnnotationsList().iterator();
            String allMerchants[] = annotation.next().getDescription().split("[\n\r\t]");
            if(allMerchants.length != 0){
                merchantName = allMerchants[0].split(" ")[0];
                for(int i = allMerchants.length-1;i>=0;i--){
                    out.printf("allMerchants: %s\n", allMerchants[i]);
                    Matcher amt = amountRecognizer.matcher(allMerchants[i]);
                    if (amt.find()){
                        amount = new BigDecimal(amt.group());
                        break;
                    }
                }
            }

            if (amount == null){
                amount = new BigDecimal(0.00);
            }
            out.printf("Merchant: %s\n", merchantName);
            out.printf("Amount: %s\n", amount);

            int orientation = GetOrientation(res);
            out.printf("orientation: %s\n", orientation);
            int[] boundingBox = new int[5];
            boundingBox[0]=res.getTextAnnotationsList().get(0).getBoundingPoly().getVertices(0).getX();
            boundingBox[1]=res.getTextAnnotationsList().get(0).getBoundingPoly().getVertices(1).getX();;
            boundingBox[2]=res.getTextAnnotationsList().get(0).getBoundingPoly().getVertices(0).getY();
            boundingBox[3]=res.getTextAnnotationsList().get(0).getBoundingPoly().getVertices(2).getY();
            boundingBox[4]=orientation;


            /*BoundingPoly allBoundingPoly = annotation.next().getBoundingPoly();
            //.getVertices(0).getX()

            int[] boundingBox = new int[4];
            float centerX = 0, centerY = 0;
            int minX = allBoundingPoly.getVertices(0).getX(), minY = allBoundingPoly.getVertices(0).getY();
            int maxX = 0, maxY = 0;
            for (int i = 0; i < 4; i++) {
                centerX += allBoundingPoly.getVertices(i).getX();
                centerY += allBoundingPoly.getVertices(i).getY();
                if (minX>allBoundingPoly.getVertices(i).getX()){
                    minX = allBoundingPoly.getVertices(i).getX();
                }
                if (minY>allBoundingPoly.getVertices(i).getY()){
                    minY = allBoundingPoly.getVertices(i).getY();
                }
                if (maxX<allBoundingPoly.getVertices(i).getX()){
                    maxX = allBoundingPoly.getVertices(i).getX();
                }
                if (maxY<allBoundingPoly.getVertices(i).getY()){
                    maxY = allBoundingPoly.getVertices(i).getY();
                }
            }
            centerX /= 4;
            centerY /= 4;

            int x0 = allBoundingPoly.getVertices(0).getX();
            int y0 = allBoundingPoly.getVertices(0).getY();
            if (x0 < centerX) {
                if (y0 < centerY) {
                    //https://stackoverflow.com/questions/41285556/get-correct-image-orientation-by-google-cloud-vision-api-text-detection/41556922
                    //       0 -------- 1
                    //       |          |
                    //       3 -------- 2
                    //orientation - normal
                    boundingBox[0] = minX; 
                    boundingBox[1] = minY; 
                    boundingBox[2] = maxX; 
                    boundingBox[3] = maxY; 
                    out.printf("Bounding Box1: %d%d%d%d\n", boundingBox[0],boundingBox[1],boundingBox[2],boundingBox[3]);
                    } 
                else {
                    //       1 -------- 2
                    //       |          |
                    //       0 -------- 3
                    //orientation - 270deg
                    boundingBox[0] = minY; 
                    boundingBox[1] = maxX - minX; 
                    boundingBox[2] = maxY - minY; 
                    boundingBox[3] = minX; 
                    out.printf("Bounding Box2: %d%d%d%d\n", boundingBox[0],boundingBox[1],boundingBox[2],boundingBox[3]);
                }
            } 
            else {
                if (y0 < centerY) {
                    //       3 -------- 0
                    //       |          |
                    //       2 -------- 1
                    //orientation - 90deg
                    boundingBox[0] = maxY - minY; 
                    boundingBox[1] = minX; 
                    boundingBox[2] = minY; 
                    boundingBox[3] = maxX - minX; 
                    out.printf("Bounding Box3: %d%d%d%d\n", boundingBox[0],boundingBox[1],boundingBox[2],boundingBox[3]);
                } 
                else {
                    //       2 -------- 3
                    //       |          |
                    //       1 -------- 0
                    //orientation - 180deg
                    boundingBox[0] = maxX - minX; 
                    boundingBox[1] = maxY - minY; 
                    boundingBox[2] = minX; 
                    boundingBox[3] = minY; 
                    out.printf("Bounding Box4: %d%d%d%d\n", boundingBox[0],boundingBox[1],boundingBox[2],boundingBox[3]);
                }
            }*/

            //for (EntityAnnotation annotation : res.getTextAnnotationsList()) {

                //getting all text and corresponding positions
                //out.printf("Position : %s\n", annotation.getBoundingPoly());
                //out.printf("Text: %s\n", annotation.getDescription());

                //String merchant = annotation.get(0).getDescription();
                //BigDecimal amount = 
            //}

            // Not the right thing, but here for now.
            //TextAnnotation fullTextAnnotation = res.getFullTextAnnotation();
            //return fullTextAnnotation.getText();
            return new ReceiptSuggestionResponse(merchantName, amount, boundingBox);
        }
    }
}
