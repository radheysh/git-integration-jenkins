package com.fti.usdg.track.trace.dto;

public class ShipmentDataVersionsDto {

	public ShipmentDataVersionsDto() {

	}

	private String transactionHash = null;
	private String createdAt = null;
	private String bolNo = null;
	private Integer version = null;
	private String channelName = null;
	private String blockNo = null;
	private ShipmentDataDto data = null;

	public String getTransactionHash() {
		return transactionHash;
	}

	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getBolNo() {
		return bolNo;
	}

	public void setBolNo(String bolNo) {
		this.bolNo = bolNo;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getBlockNo() {
		return blockNo;
	}

	public void setBlockNo(String blockNo) {
		this.blockNo = blockNo;
	}

	public ShipmentDataDto getData() {
		return data;
	}

	public void setData(ShipmentDataDto data) {
		this.data = data;
	}

}