/**
 * SyncOrderRelationRespType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.changyuan.misc.model.bean;

public class SyncOrderRelationRespType  implements java.io.Serializable {
    private java.lang.String msgType;

    private java.lang.String version;

    private java.math.BigInteger hRet;

    public SyncOrderRelationRespType() {
    }

    public SyncOrderRelationRespType(
           java.lang.String msgType,
           java.lang.String version,
           java.math.BigInteger hRet) {
           this.msgType = msgType;
           this.version = version;
           this.hRet = hRet;
    }


    /**
     * Gets the msgType value for this SyncOrderRelationRespType.
     * 
     * @return msgType
     */
    public java.lang.String getMsgType() {
        return msgType;
    }


    /**
     * Sets the msgType value for this SyncOrderRelationRespType.
     * 
     * @param msgType
     */
    public void setMsgType(java.lang.String msgType) {
        this.msgType = msgType;
    }


    /**
     * Gets the version value for this SyncOrderRelationRespType.
     * 
     * @return version
     */
    public java.lang.String getVersion() {
        return version;
    }


    /**
     * Sets the version value for this SyncOrderRelationRespType.
     * 
     * @param version
     */
    public void setVersion(java.lang.String version) {
        this.version = version;
    }


    /**
     * Gets the hRet value for this SyncOrderRelationRespType.
     * 
     * @return hRet
     */
    public java.math.BigInteger getHRet() {
        return hRet;
    }


    /**
     * Sets the hRet value for this SyncOrderRelationRespType.
     * 
     * @param hRet
     */
    public void setHRet(java.math.BigInteger hRet) {
        this.hRet = hRet;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SyncOrderRelationRespType)) return false;
        SyncOrderRelationRespType other = (SyncOrderRelationRespType) obj;
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
            ((this.hRet==null && other.getHRet()==null) || 
             (this.hRet!=null &&
              this.hRet.equals(other.getHRet())));
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
        if (getHRet() != null) {
            _hashCode += getHRet().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

//    // Type metadata
//    private static org.apache.axis.description.TypeDesc typeDesc =
//        new org.apache.axis.description.TypeDesc(SyncOrderRelationRespType.class, true);
//
//    static {
//        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "SyncOrderRelationRespType"));
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
//        elemField.setFieldName("HRet");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "hRet"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
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
