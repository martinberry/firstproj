package com.ztravel.test.gen.po;

public class OrderProduct {
    private String snapshotId;

    private String orderId;

    private String productId;

    private String productTitle;

    private String productType;

    private String bookDate;

    private String productSnapshot;

    public String getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId == null ? null : snapshotId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle == null ? null : productTitle.trim();
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate == null ? null : bookDate.trim();
    }

    public String getProductSnapshot() {
        return productSnapshot;
    }

    public void setProductSnapshot(String productSnapshot) {
        this.productSnapshot = productSnapshot == null ? null : productSnapshot.trim();
    }
}