package com.aalto.protocol.design.iotps.objects;

import com.aalto.protocol.design.iotps.json.engine.JSON_Object;
import com.aalto.protocol.design.iotps.utils.Constants;

public class IoTPSUpdateObject extends IoTPSObject{
	private String client_ip;
	private int client_port;
	private String device_id;
	private long seq_no;
	private long sub_seq_no;
	private long timestamp;
	private String sensor_data;
	private int version;
	private int ack_support;
	
	public String getClientIp() {return client_ip;}
	public void setClientIp(String ip) {this.client_ip = ip;}
	
	public int getClientPort() {return client_port;}
	public void setClientPort(int port) {this.client_port = port;}
	
	public String getDeviceId() {return device_id;}
	public void setDeviceId(String id) {this.device_id = id;}
	
	public long getSeqNo() {return seq_no;}
	public void setSeqNo(long seq) {this.seq_no = seq;}
	
	public long getSubSeqNo() {return sub_seq_no;}
	public void setSubSeqNo(long sub) {this.sub_seq_no = sub;}
	
	public long getTimestamp() {return timestamp;}
	public void setTimestamp(long ts) {this.timestamp = ts;}
	
	public String getSensorData() {return sensor_data;}
	public void setSensorData(String data) {this.sensor_data = data;}
	
	public int getVersion() {return version;}
	public void setVersion(int v) {this.version = v;}
	
	public int getAckSupport() {return ack_support;}
	public void setAckSupport(int a) {this.ack_support = a;}
	
	
	public String getJSONString() {
		JSON_Object o = this.getJSONObject();
		return o.toJSONString();
	}
	
	public JSON_Object getJSONObject() {
		JSON_Object o = new JSON_Object();
		o.AddItem(Constants.ACTION, Constants.UPDATE);
		o.AddItem("client_ip", this.client_ip);
		o.AddItem("client_port", this.client_port + "");
		o.AddItem("dev_id", this.device_id);
		o.AddItem("seq_no", this.getSeqNo() + "");
		o.AddItem("sub_seq_no", this.getSubSeqNo() + "");
		o.AddItem("timestamp", this.getTimestamp() + "");
		o.AddItem("sensor_data", this.getSensorData());
		o.AddItem("version", this.getVersion() + "");
		return o;
	}
}
