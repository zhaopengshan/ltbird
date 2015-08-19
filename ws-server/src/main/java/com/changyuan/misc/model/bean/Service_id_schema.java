/**
 * Service_id_schema.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.changyuan.misc.model.bean;

public class Service_id_schema  implements java.io.Serializable {
    private java.math.BigInteger serviceIDType;

    private java.lang.String SPID;

    private java.lang.String SPServiceID;

    private java.lang.String accessNo;

    private byte[] featureStr;

    public Service_id_schema() {
    }

    public Service_id_schema(
           java.math.BigInteger serviceIDType,
           java.lang.String SPID,
           java.lang.String SPServiceID,
           java.lang.String accessNo,
           byte[] featureStr) {
           this.serviceIDType = serviceIDType;
           this.SPID = SPID;
           this.SPServiceID = SPServiceID;
           this.accessNo = accessNo;
           this.featureStr = featureStr;
    }


    /**
     * Gets the serviceIDType value for this Service_id_schema.
     * 
     * @return serviceIDType
     */
    public java.math.BigInteger getServiceIDType() {
        return serviceIDType;
    }


    /**
     * Sets the serviceIDType value for this Service_id_schema.
     * 
     * @param serviceIDType
     */
    public void setServiceIDType(java.math.BigInteger serviceIDType) {
        this.serviceIDType = serviceIDType;
    }


    /**
     * Gets the SPID value for this Service_id_schema.
     * 
     * @return SPID
     */
    public java.lang.String getSPID() {
        return SPID;
    }


    /**
     * Sets the SPID value for this Service_id_schema.
     * 
     * @param SPID
     */
    public void setSPID(java.lang.String SPID) {
        this.SPID = SPID;
    }


    /**
     * Gets the SPServiceID value for this Service_id_schema.
     * 
     * @return SPServiceID
     */
    public java.lang.String getSPServiceID() {
        return SPServiceID;
    }


    /**
     * Sets the SPServiceID value for this Service_id_schema.
     * 
     * @param SPServiceID
     */
    public void setSPServiceID(java.lang.String SPServiceID) {
        this.SPServiceID = SPServiceID;
    }


    /**
     * Gets the accessNo value for this Service_id_schema.
     * 
     * @return accessNo
     */
    public java.lang.String getAccessNo() {
        return accessNo;
    }


    /**
     * Sets the accessNo value for this Service_id_schema.
     * 
     * @param accessNo
     */
    public void setAccessNo(java.lang.String accessNo) {
        this.accessNo = accessNo;
    }


    /**
     * Gets the featureStr value for this Service_id_schema.
     * 
     * @return featureStr
     */
    public byte[] getFeatureStr() {
        return featureStr;
    }


    /**
     * Sets the featureStr value for this Service_id_schema.
     * 
     * @param featureStr
     */
    public void setFeatureStr(byte[] featureStr) {
        this.featureStr = featureStr;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Service_id_schema)) return false;
        Service_id_schema other = (Service_id_schema) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.serviceIDType==null && other.getServiceIDType()==null) || 
             (this.serviceIDType!=null &&
              this.serviceIDType.equals(other.getServiceIDType()))) &&
            ((this.SPID==null && other.getSPID()==null) || 
             (this.SPID!=null &&
              this.SPID.equals(other.getSPID()))) &&
            ((this.SPServiceID==null && other.getSPServiceID()==null) || 
             (this.SPServiceID!=null &&
              this.SPServiceID.equals(other.getSPServiceID()))) &&
            ((this.accessNo==null && other.getAccessNo()==null) || 
             (this.accessNo!=null &&
              this.accessNo.equals(other.getAccessNo()))) &&
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
        if (getServiceIDType() != null) {
            _hashCode += getServiceIDType().hashCode();
        }
        if (getSPID() != null) {
            _hashCode += getSPID().hashCode();
        }
        if (getSPServiceID() != null) {
            _hashCode += getSPServiceID().hashCode();
        }
        if (getAccessNo() != null) {
            _hashCode += getAccessNo().hashCode();
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
//        new org.apache.axis.description.TypeDesc(Service_id_schema.class, true);
//
//    static {
//        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "service_id_schema"));
//        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("serviceIDType");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "ServiceIDType"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("SPID");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "SPID"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("SPServiceID");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "SPServiceID"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("accessNo");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "AccessNo"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("featureStr");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "FeatureStr"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
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
