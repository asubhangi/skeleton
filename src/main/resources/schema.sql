CREATE TABLE IF NOT EXISTS receipts (
  id INT UNSIGNED AUTO_INCREMENT,
  uploaded DATETIME DEFAULT CURRENT_TIME(),
  merchant VARCHAR(255),
  amount DECIMAL(12,2),
  images LONGVARCHAR(MAX),
  receipt_type INT UNSIGNED,

  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tags (
  pid INT UNSIGNED AUTO_INCREMENT,
  name VARCHAR(255),
  id INT UNSIGNED,

  PRIMARY KEY (pid),
  CONSTRAINT fk_receipt FOREIGN KEY (id) REFERENCES receipts (id)  

);

select * from receipts;
select * from tags;