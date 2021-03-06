/**
 * This class is generated by jOOQ
 */
package generated.tables.records;


import generated.tables.Receipts;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.4"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ReceiptsRecord extends UpdatableRecordImpl<ReceiptsRecord> implements Record6<Integer, Timestamp, String, BigDecimal, String, Integer> {

	private static final long serialVersionUID = 774640959;

	/**
	 * Setter for <code>public.receipts.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>public.receipts.id</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>public.receipts.uploaded</code>.
	 */
	public void setUploaded(Timestamp value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>public.receipts.uploaded</code>.
	 */
	public Timestamp getUploaded() {
		return (Timestamp) getValue(1);
	}

	/**
	 * Setter for <code>public.receipts.merchant</code>.
	 */
	public void setMerchant(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>public.receipts.merchant</code>.
	 */
	public String getMerchant() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>public.receipts.amount</code>.
	 */
	public void setAmount(BigDecimal value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>public.receipts.amount</code>.
	 */
	public BigDecimal getAmount() {
		return (BigDecimal) getValue(3);
	}

	/**
	 * Setter for <code>public.receipts.images</code>.
	 */
	public void setImages(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>public.receipts.images</code>.
	 */
	public String getImages() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>public.receipts.receipt_type</code>.
	 */
	public void setReceiptType(Integer value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>public.receipts.receipt_type</code>.
	 */
	public Integer getReceiptType() {
		return (Integer) getValue(5);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Integer> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record6 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row6<Integer, Timestamp, String, BigDecimal, String, Integer> fieldsRow() {
		return (Row6) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row6<Integer, Timestamp, String, BigDecimal, String, Integer> valuesRow() {
		return (Row6) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Receipts.RECEIPTS.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field2() {
		return Receipts.RECEIPTS.UPLOADED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return Receipts.RECEIPTS.MERCHANT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigDecimal> field4() {
		return Receipts.RECEIPTS.AMOUNT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return Receipts.RECEIPTS.IMAGES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field6() {
		return Receipts.RECEIPTS.RECEIPT_TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value2() {
		return getUploaded();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getMerchant();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal value4() {
		return getAmount();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getImages();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value6() {
		return getReceiptType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReceiptsRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReceiptsRecord value2(Timestamp value) {
		setUploaded(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReceiptsRecord value3(String value) {
		setMerchant(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReceiptsRecord value4(BigDecimal value) {
		setAmount(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReceiptsRecord value5(String value) {
		setImages(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReceiptsRecord value6(Integer value) {
		setReceiptType(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReceiptsRecord values(Integer value1, Timestamp value2, String value3, BigDecimal value4, String value5, Integer value6) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ReceiptsRecord
	 */
	public ReceiptsRecord() {
		super(Receipts.RECEIPTS);
	}

	/**
	 * Create a detached, initialised ReceiptsRecord
	 */
	public ReceiptsRecord(Integer id, Timestamp uploaded, String merchant, BigDecimal amount, String images, Integer receiptType) {
		super(Receipts.RECEIPTS);

		setValue(0, id);
		setValue(1, uploaded);
		setValue(2, merchant);
		setValue(3, amount);
		setValue(4, images);
		setValue(5, receiptType);
	}
}
