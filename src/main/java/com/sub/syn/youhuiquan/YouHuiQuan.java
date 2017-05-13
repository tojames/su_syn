package com.sub.syn.youhuiquan;

import java.util.Date;

public class YouHuiQuan {

	private int id;
	
	/**淘宝ID**/
	private long goodsId;
	
	private String title;
	
	/**短标题**/
	private String ditle;
	
	/***商品主图**/
	private String pic;
	/***分类ID**/
	private int cid;
	/**正常售价**/
	private double orgPrice;
	/**券后价****/
	private double price;
	/**是否天猫**/
	private int isTmall;
	/***销量**/
	private int salesNum;
	/**商品描述分**/
	private String dsr;
	/***卖家ID**/
	private String sellerID;
	/**商品文案**/
	private String introduce;
	/**券金额**/
	private double quanPrice;
	/***券使用条件**/
	private String quanCondition;
	/**手机券连接**/
	private String quanLink;
	/***手机券短链***/
	private String qQuanMLink;
	/***淘宝客连接**/
	private String aliClick;
	/**商品来源 1大淘客***/
	private int type;
	/***优惠券剩余数量**/
	private int quanSurplus;
	/***结束时间**/
	private Date quanTime;
	private Date createTime;
	
	/**分类名称**/
	private String cateName;
	/**备注**/
	private String note;
	public int getId() {
		return id;
	}

	/***商品源Id**/
	public void setId(int id) {
		this.id = id;
	}

	public long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}

	public String getDitle() {
		return ditle;
	}

	public void setDitle(String ditle) {
		this.ditle = ditle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getD_title() {
		return ditle;
	}

	public void setD_title(String d_title) {
		ditle = d_title;
	}

	public String getPic() {
		return pic;
	}

	public int getQuanSurplus() {
		return quanSurplus;
	}

	public void setQuanSurplus(int quanSurplus) {
		this.quanSurplus = quanSurplus;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public int getCid() {
		return cid;
	}

	/**分类ID***/
	public void setCid(int cid) {
		this.cid = cid;
	}

	public double getOrgPrice() {
		return orgPrice;
	}

	public void setOrgPrice(double orgPrice) {
		this.orgPrice = orgPrice;
	}

	public double getPrice() {
		return price;
	}
	/*券后价*/
	public void setPrice(double price) {
		this.price = price;
	}

	public int getIsTmall() {
		return isTmall;
	}

	public void setIsTmall(int isTmall) {
		this.isTmall = isTmall;
	}

	public int getSalesNum() {
		return salesNum;
	}

	public void setSalesNum(int salesNum) {
		this.salesNum = salesNum;
	}

	public String getDsr() {
		return dsr;
	}

	public void setDsr(String dsr) {
		this.dsr = dsr;
	}

	public String getSellerID() {
		return sellerID;
	}

	public void setSellerID(String sellerID) {
		this.sellerID = sellerID;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public double getQuanPrice() {
		return quanPrice;
	}

	public void setQuanPrice(double quanPrice) {
		this.quanPrice = quanPrice;
	}

	public String getQuanCondition() {
		return quanCondition;
	}

	public void setQuanCondition(String quanCondition) {
		this.quanCondition = quanCondition;
	}

	public String getQuanLink() {
		return quanLink;
	}

	public void setQuanLink(String quanLink) {
		this.quanLink = quanLink;
	}

	public String getqQuanMLink() {
		return qQuanMLink;
	}

	public void setqQuanMLink(String qQuanMLink) {
		this.qQuanMLink = qQuanMLink;
	}

	public String getAliClick() {
		return aliClick;
	}

	public void setAliClick(String aliClick) {
		this.aliClick = aliClick;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getQuanTime() {
		return quanTime;
	}

	public void setQuanTime(Date quanTime) {
		this.quanTime = quanTime;
	}

	public String getCateName() {
		return cateName;
	}
	/**分类名称**/
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
