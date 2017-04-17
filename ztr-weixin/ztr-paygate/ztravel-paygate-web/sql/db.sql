--add by guangxian.ding  2013/12/04

-- Table: paygate.trade_batch_bill

-- DROP TABLE paygate.trade_batch_bill;

CREATE TABLE paygate.trade_batch_bill
(
  batch_bill_id character varying(24) NOT NULL, -- 账单ID
  partner character varying(32), -- 商户号
  gate_type character varying(10), -- 支付平台(Tenpay/AliPay/Chinapnr)
  date_bill_id character varying(24), -- 日结账单ID
  trans_date character varying(10), -- 交易日期(yyyyMMdd)
  req_ret_code character varying(10), -- 签名结果
  req_ret_msg character varying(200), -- 签名结果信息
  req_success boolean, -- 是否请求成功
  bill_store_id character varying(32), -- 账单存储ID
  page_no integer, -- 页号
  has_next_page boolean, -- 是否存在下页数据
  create_time timestamp with time zone, -- 创建时间
  CONSTRAINT pk_trade_batch_bill PRIMARY KEY (batch_bill_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE paygate.trade_batch_bill
  OWNER TO postgres;
COMMENT ON TABLE paygate.trade_batch_bill
  IS '交易账单表';
COMMENT ON COLUMN paygate.trade_batch_bill.batch_bill_id IS '账单ID';
COMMENT ON COLUMN paygate.trade_batch_bill.partner IS '商户号';
COMMENT ON COLUMN paygate.trade_batch_bill.gate_type IS '支付平台(Tenpay/AliPay/Chinapnr)';
COMMENT ON COLUMN paygate.trade_batch_bill.date_bill_id IS '日结账单ID';
COMMENT ON COLUMN paygate.trade_batch_bill.trans_date IS '交易日期(yyyyMMdd)';
COMMENT ON COLUMN paygate.trade_batch_bill.req_ret_code IS '签名结果';
COMMENT ON COLUMN paygate.trade_batch_bill.req_ret_msg IS '签名结果信息';
COMMENT ON COLUMN paygate.trade_batch_bill.req_success IS '是否请求成功';
COMMENT ON COLUMN paygate.trade_batch_bill.bill_store_id IS '账单存储ID';
COMMENT ON COLUMN paygate.trade_batch_bill.page_no IS '页号';
COMMENT ON COLUMN paygate.trade_batch_bill.has_next_page IS '是否存在下页数据';
COMMENT ON COLUMN paygate.trade_batch_bill.create_time IS '创建时间';

-- Table: paygate.trade_date_bill

-- DROP TABLE paygate.trade_date_bill;

CREATE TABLE paygate.trade_date_bill
(
  date_bill_id character varying(24) NOT NULL, -- 日结账单ID
  partner character varying(32), -- 商户号
  gate_type character varying(10), -- 支付平台(Tenpay/AliPay/Chinapnr)
  trans_date character varying(10), -- 交易日期(yyyyMMdd)
  bill_store_id character varying(32), -- 账单文件存储ID
  is_checked boolean, -- 是否已对账
  checked_msg character varying(200), -- 对账信息
  create_time timestamp with time zone, -- 创建时间
  CONSTRAINT pk_trade_date_bill PRIMARY KEY (date_bill_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE paygate.trade_date_bill
  OWNER TO postgres;
COMMENT ON TABLE paygate.trade_date_bill
  IS '交易日结账单表';
COMMENT ON COLUMN paygate.trade_date_bill.date_bill_id IS '日结账单ID';
COMMENT ON COLUMN paygate.trade_date_bill.partner IS '商户号';
COMMENT ON COLUMN paygate.trade_date_bill.gate_type IS '支付平台(Tenpay/AliPay/Chinapnr)';
COMMENT ON COLUMN paygate.trade_date_bill.trans_date IS '交易日期(yyyyMMdd)';
COMMENT ON COLUMN paygate.trade_date_bill.bill_store_id IS '账单文件存储ID';
COMMENT ON COLUMN paygate.trade_date_bill.is_checked IS '是否已对账';
COMMENT ON COLUMN paygate.trade_date_bill.checked_msg IS '对账信息';
COMMENT ON COLUMN paygate.trade_date_bill.create_time IS '创建时间';

-- Table: paygate.trade_date_bill_record

-- DROP TABLE paygate.trade_date_bill_record;

CREATE TABLE paygate.trade_date_bill_record
(
  date_bill_record_id character varying(24) NOT NULL, -- 交易记录ID
  date_bill_id character varying(32), -- 账单ID
  trans_time timestamp with time zone, -- 交易时间
  order_num character varying(64), -- 商户订单号
  trace_num character varying(36), -- 网支机构订单号
  refund_num character varying(64), -- 退款标识号
  bank_num character varying(64), -- 银行订单号
  trade_type character varying(32), -- 交易类型
  origin_trade_type character varying(64), -- 交易类型(网支机构)
  trade_income numeric(18,0), -- 交易收入
  trade_expend numeric(18,0), -- 交易支出
  trans_comment character varying(100), -- 交易说明
  trans_status character varying(24), -- 交易状态
  trans_status_msg character varying(100), -- 交易状态信息
  checked_status character varying(32), -- 对账状态
  checked_msg character varying(200), -- 对账信息
  create_time timestamp with time zone, -- 创建时间
  checked_time timestamp with time zone, -- 对账时间
  CONSTRAINT pk_trade_date_bill_record PRIMARY KEY (date_bill_record_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE paygate.trade_date_bill_record
  OWNER TO postgres;
COMMENT ON TABLE paygate.trade_date_bill_record
  IS '日结交易记录表';
COMMENT ON COLUMN paygate.trade_date_bill_record.date_bill_record_id IS '交易记录ID';
COMMENT ON COLUMN paygate.trade_date_bill_record.date_bill_id IS '账单ID';
COMMENT ON COLUMN paygate.trade_date_bill_record.trans_time IS '交易时间';
COMMENT ON COLUMN paygate.trade_date_bill_record.order_num IS '商户订单号';
COMMENT ON COLUMN paygate.trade_date_bill_record.trace_num IS '网支机构订单号';
COMMENT ON COLUMN paygate.trade_date_bill_record.refund_num IS '退款标识号';
COMMENT ON COLUMN paygate.trade_date_bill_record.bank_num IS '银行订单号';
COMMENT ON COLUMN paygate.trade_date_bill_record.trade_type IS '交易类型';
COMMENT ON COLUMN paygate.trade_date_bill_record.origin_trade_type IS '交易类型(网支机构)';
COMMENT ON COLUMN paygate.trade_date_bill_record.trade_income IS '交易收入';
COMMENT ON COLUMN paygate.trade_date_bill_record.trade_expend IS '交易支出';
COMMENT ON COLUMN paygate.trade_date_bill_record.trans_comment IS '交易说明';
COMMENT ON COLUMN paygate.trade_date_bill_record.trans_status IS '交易状态';
COMMENT ON COLUMN paygate.trade_date_bill_record.trans_status_msg IS '交易状态信息';
COMMENT ON COLUMN paygate.trade_date_bill_record.checked_status IS '对账状态';
COMMENT ON COLUMN paygate.trade_date_bill_record.checked_msg IS '对账信息';
COMMENT ON COLUMN paygate.trade_date_bill_record.create_time IS '创建时间';
COMMENT ON COLUMN paygate.trade_date_bill_record.checked_time IS '对账时间';

