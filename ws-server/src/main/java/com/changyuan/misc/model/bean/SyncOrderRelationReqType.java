/**
 * SyncOrderRelationReqType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.changyuan.misc.model.bean;

public class SyncOrderRelationReqType  implements java.io.Serializable {
    private java.lang.String msgType;

    private java.lang.String version;

    private Address_info_schema send_Address;

    private Address_info_schema dest_Address;

    private User_id_schema feeUser_ID;

    private User_id_schema destUser_ID;

    private java.lang.String linkID;

    private java.math.BigInteger actionID;

    private java.math.BigInteger actionReasonID;

    private java.lang.String SPID;

    private java.lang.String SPServiceID;

    private java.math.BigInteger accessMode;

    private byte[] featureStr;

    public SyncOrderRelationReqType() {
    }

    public SyncOrderRelationReqType(
           java.lang.String msgType,
           java.lang.String version,
           Address_info_schema send_Address,
           Address_info_schema dest_Address,
           User_id_schema feeUser_ID,
           User_id_schema destUser_ID,
           java.lang.String linkID,
           java.math.BigInteger actionID,
           java.math.BigInteger actionReasonID,
           java.lang.String SPID,
           java.lang.String SPServiceID,
           java.math.BigInteger accessMode,
           byte[] featureStr) {
           this.msgType = msgType;
           this.version = version;
           this.send_Address = send_Address;
           this.dest_Address = dest_Address;
           this.feeUser_ID = feeUser_ID;
           this.destUser_ID = destUser_ID;
           this.linkID = linkID;
           this.actionID = actionID;
           this.actionReasonID = actionReasonID;
           this.SPID = SPID;
           this.SPServiceID = SPServiceID;
           this.accessMode = accessMode;
           this.featureStr = featureStr;
    }


    /**
     * Gets the msgType value for this SyncOrderRelationReqType.
     * 
     * @return msgType
     */
    public java.lang.String getMsgType() {
        return msgType;
    }


    /**
     * Sets the msgType value for this SyncOrderRelationReqType.
     * 
     * @param msgType
     */
    public void setMsgType(java.lang.String msgType) {
        this.msgType = msgType;
    }


    /**
     * Gets the version value for this SyncOrderRelationReqType.
     * 
     * @return version
     */
    public java.lang.String getVersion() {
        return version;
    }


    /**
     * Sets the version value for this SyncOrderRelationReqType.
     * 
     * @param version
     */
    public void setVersion(java.lang.String version) {
        this.version = version;
    }


    /**
     * Gets the send_Address value for this SyncOrderRelationReqType.
     * 
     * @return send_Address
     */
    public Address_info_schema getSend_Address() {
        return send_Address;
    }


    /**
     * Sets the send_Address value for this SyncOrderRelationReqType.
     * 
     * @param send_Address
     */
    public void setSend_Address(Address_info_schema send_Address) {
        this.send_Address = send_Address;
    }


    /**
     * Gets the dest_Address value for this SyncOrderRelationReqType.
     * 
     * @return dest_Address
     */
    public Address_info_schema getDest_Address() {
        return dest_Address;
    }


    /**
     * Sets the dest_Address value for this SyncOrderRelationReqType.
     * 
     * @param dest_Address
     */
    public void setDest_Address(Address_info_schema dest_Address) {
        this.dest_Address = dest_Address;
    }


    /**
     * Gets the feeUser_ID value for this SyncOrderRelationReqType.
     * 
     * @return feeUser_ID
     */
    public User_id_schema getFeeUser_ID() {
        return feeUser_ID;
    }


    /**
     * Sets the feeUser_ID value for this SyncOrderRelationReqType.
     * 
     * @param feeUser_ID
     */
    public void setFeeUser_ID(User_id_schema feeUser_ID) {
        this.feeUser_ID = feeUser_ID;
    }


    /**
     * Gets the destUser_ID value for this SyncOrderRelationReqType.
     * 
     * @return destUser_ID
     */
    public User_id_schema getDestUser_ID() {
        return destUser_ID;
    }


    /**
     * Sets the destUser_ID value for this SyncOrderRelationReqType.
     * 
     * @param destUser_ID
     */
    public void setDestUser_ID(User_id_schema destUser_ID) {
        this.destUser_ID = destUser_ID;
    }


    /**
     * Gets the linkID value for this SyncOrderRelationReqType.
     * 
     * @return linkID
     */
    public java.lang.String getLinkID() {
        return linkID;
    }


    /**
     * Sets the linkID value for this SyncOrderRelationReqType.
     * 
     * @param linkID
     */
    public void setLinkID(java.lang.String linkID) {
        this.linkID = linkID;
    }


    /**
     * Gets the actionID value for this SyncOrderRelationReqType.
     * 
     * @return actionID
     */
    public java.math.BigInteger getActionID() {
        return actionID;
    }


    /**
     * Sets the actionID value for this SyncOrderRelationReqType.
     * 
     * @param actionID
     */
    public void setActionID(java.math.BigInteger actionID) {
        this.actionID = actionID;
    }


    /**
     * Gets the actionReasonID value for this SyncOrderRelationReqType.
     * 
     * @return actionReasonID
     */
    public java.math.BigInteger getActionReasonID() {
        return actionReasonID;
    }


    /**
     * Sets the actionReasonID value for this SyncOrderRelationReqType.
     * 
     * @param actionReasonID
     */
    public void setActionReasonID(java.math.BigInteger actionReasonID) {
        this.actionReasonID = actionReasonID;
    }


    /**
     * Gets the SPID value for this SyncOrderRelationReqType.
     * 
     * @return SPID
     */
    public java.lang.String getSPID() {
        return SPID;
    }


    /**
     * Sets the SPID value for this SyncOrderRelationReqType.
     * 
     * @param SPID
     */
    public void setSPID(java.lang.String SPID) {
        this.SPID = SPID;
    }


    /**
     * Gets the SPServiceID value for this SyncOrderRelationReqType.
     * 
     * @return SPServiceID
     */
    public java.lang.String getSPServiceID() {
        return SPServiceID;
    }


    /**
     * Sets the SPServiceID value for this SyncOrderRelationReqType.
     * 
     * @param SPServiceID
     */
    public void setSPServiceID(java.lang.String SPServiceID) {
        this.SPServiceID = SPServiceID;
    }


    /**
     * Gets the accessMode value for this SyncOrderRelationReqType.
     * 
     * @return accessMode
     */
    public java.math.BigInteger getAccessMode() {
        return accessMode;
    }


    /**
     * Sets the accessMode value for this SyncOrderRelationReqType.
     * 
     * @param accessMode
     */
    public void setAccessMode(java.math.BigInteger accessMode) {
        this.accessMode = accessMode;
    }


    /**
     * Gets the featureStr value for this SyncOrderRelationReqType.
     * 
     * @return featureStr
     */
    public byte[] getFeatureStr() {
        return featureStr;
    }


    /**
     * Sets the featureStr value for this SyncOrderRelationReqType.
     * 
     * @param featureStr
     */
    public void setFeatureStr(byte[] featureStr) {
        this.featureStr = featureStr;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SyncOrderRelationReqType)) return false;
        SyncOrderRelationReqType other = (SyncOrderRelationReqType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.msgType==null && other.getMsgType()==null) || 
             (this.msgType!=null &&
              this.msgType.equals(other.getMsgType()))) &&
            ((this.version==null && other.getVersion()==null) || 
             (this.version!=null &&
              this.version.equals(other.getVersion()))) &&
            ((this.send_Address==null && other.getSend_Address()==null) || 
             (this.send_Address!=null &&
              this.send_Address.equals(other.getSend_Address()))) &&
            ((this.dest_Address==null && other.getDest_Address()==null) || 
             (this.dest_Address!=null &&
              this.dest_Address.equals(other.getDest_Address()))) &&
            ((this.feeUser_ID==null && other.getFeeUser_ID()==null) || 
             (this.feeUser_ID!=null &&
              this.feeUser_ID.equals(other.getFeeUser_ID()))) &&
            ((this.destUser_ID==null && other.getDestUser_ID()==null) || 
             (this.destUser_ID!=null &&
              this.destUser_ID.equals(other.getDestUser_ID()))) &&
            ((this.linkID==null && other.getLinkID()==null) || 
             (this.linkID!=null &&
              this.linkID.equals(other.getLinkID()))) &&
            ((this.actionID==null && other.getActionID()==null) || 
             (this.actionID!=null &&
              this.actionID.equals(other.getActionID()))) &&
            ((this.actionReasonID==null && other.getActionReasonID()==null) || 
             (this.actionReasonID!=null &&
              this.actionReasonID.equals(other.getActionReasonID()))) &&
            ((this.SPID==null && other.getSPID()==null) || 
             (this.SPID!=null &&
              this.SPID.equals(other.getSPID()))) &&
            ((this.SPServiceID==null && other.getSPServiceID()==null) || 
             (this.SPServiceID!=null &&
              this.SPServiceID.equals(other.getSPServiceID()))) &&
            ((this.accessMode==null && other.getAccessMode()==null) || 
             (this.accessMode!=null &&
              this.accessMode.equals(other.getAccessMode()))) &&
            ((this.featureStr==null && other.getFeatureStr()==null) || 
             (this.featureStr!=null &&
              java.util.Arrays.equals(this.featureStr, other.getFeatureStr())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getMsgType() != null) {
            _hashCode += getMsgType().hashCode();
        }
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        if (getSend_Address() != null) {
            _hashCode += getSend_Address().hashCode();
        }
        if (getDest_Address() != null) {
            _hashCode += getDest_Address().hashCode();
        }
        if (getFeeUser_ID() != null) {
            _hashCode += getFeeUser_ID().hashCode();
        }
        if (getDestUser_ID() != null) {
            _hashCode += getDestUser_ID().hashCode();
        }
        if (getLinkID() != null) {
            _hashCode += getLinkID().hashCode();
        }
        if (getActionID() != null) {
            _hashCode += getActionID().hashCode();
        }
        if (getActionReasonID() != null) {
            _hashCode += getActionReasonID().hashCode();
        }
        if (getSPID() != null) {
            _hashCode += getSPID().hashCode();
        }
        if (getSPServiceID() != null) {
            _hashCode += getSPServiceID().hashCode();
        }
        if (getAccessMode() != null) {
            _hashCode += getAccessMode().hashCode();
        }
        if (getFeatureStr() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFeatureStr());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFeatureStr(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

//    // Type metadata
//    private static org.apache.axis.description.TypeDesc typeDesc =
//        new org.apache.axis.description.TypeDesc(SyncOrderRelationReqType.class, true);
//
//    static {
//        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "SyncOrderRelationReqType"));
//        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("msgType");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "MsgType"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("version");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "Version"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("send_Address");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "Send_Address"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "address_info_schema"));
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("dest_Address");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "Dest_Address"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "address_info_schema"));
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("feeUser_ID");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "FeeUser_ID"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "user_id_schema"));
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("destUser_ID");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "DestUser_ID"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "user_id_schema"));
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("linkID");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "LinkID"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
//        elemField.setMinOccurs(0);
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("actionID");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "ActionID"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("actionReasonID");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "ActionReasonID"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("SPID");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "SPID"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
//        elemField.setMinOccurs(0);
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("SPServiceID");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "SPServiceID"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("accessMode");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "AccessMode"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
//        elemField.setMinOccurs(0);
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("featureStr");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "FeatureStr"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
//        elemField.setMinOccurs(0);
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//    }
//
//    /**
//     * Return type metadata object
//     */
//    public static org.apache.axis.description.TypeDesc getTypeDesc() {
//        return typeDesc;
//    }
//
//    /**
//     * Get Custom Serializer
//     */
//    public static org.apache.axis.encoding.Serializer getSerializer(
//           java.lang.String mechType, 
//           java.lang.Class _javaType,  
//           javax.xml.namespace.QName _xmlType) {
//        return 
//          new  org.apache.axis.encoding.ser.BeanSerializer(
//            _javaType, _xmlType, typeDesc);
//    }
//
//    /**
//     * Get Custom Deserializer
//     */
//    public static org.apache.axis.encoding.Deserializer getDeserializer(
//           java.lang.String mechType, 
//           java.lang.Class _javaType,  
//           javax.xml.namespace.QName _xmlType) {
//        return 
//          new  org.apache.axis.encoding.ser.BeanDeserializer(
//            _javaType, _xmlType, typeDesc);
//    }

}
