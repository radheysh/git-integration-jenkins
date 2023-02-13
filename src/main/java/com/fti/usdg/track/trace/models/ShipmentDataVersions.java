package com.fti.usdg.track.trace.models;

import javax.persistence.*;

@Entity
@Table(name = "shipment_data_versions")
public class ShipmentDataVersions {

	public ShipmentDataVersions() {
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String transactionHash = null;
	private String createdAt = null;
	private String bolNo = null;
	private Integer version = null;
	private String channelName = null;
	private String blockNo = null;
	private String data = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}