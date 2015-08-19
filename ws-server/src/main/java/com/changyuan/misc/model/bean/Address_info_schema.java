/**
 * Address_info_schema.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.changyuan.misc.model.bean;

public class Address_info_schema  implements java.io.Serializable {
    private java.math.BigInteger deviceType;

    private java.lang.String deviceID;

    public Address_info_schema() {
    }

    public Address_info_schema(
           java.math.BigInteger deviceType,
           java.lang.String deviceID) {
           this.deviceType = deviceType;
           this.deviceID = deviceID;
    }


    /**
     * Gets the deviceType value for this Address_info_schema.
     * 
     * @return deviceType
     */
    public java.math.BigInteger getDeviceType() {
        return deviceType;
    }


    /**
     * Sets the deviceType value for this Address_info_schema.
     * 
     * @param deviceType
     */
    public void setDeviceType(java.math.BigInteger deviceType) {
        this.deviceType = deviceType;
    }


    /**
     * Gets the deviceID value for this Address_info_schema.
     * 
     * @return deviceID
     */
    public java.lang.String getDeviceID() {
        return deviceID;
    }


    /**
     * Sets the deviceID value for this Address_info_schema.
     * 
     * @param deviceID
     */
    public void setDeviceID(java.lang.String deviceID) {
        this.deviceID = deviceID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Address_info_schema)) return false;
        Address_info_schema other = (Address_info_schema) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.deviceType==null && other.getDeviceType()==null) || 
             (this.deviceType!=null &&
              this.deviceType.equals(other.getDeviceType()))) &&
            ((this.deviceID==null && other.getDeviceID()==null) || 
             (this.deviceID!=null &&
              this.deviceID.equals(other.getDeviceID())));
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
        if (getDeviceType() != null) {
            _hashCode += getDeviceType().hashCode();
        }
        if (getDeviceID() != null) {
            _hashCode += getDeviceID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

//    // Type metadata
//    private static org.apache.axis.description.TypeDesc typeDesc =
//        new org.apache.axis.description.TypeDesc(Address_info_schema.class, true);
//
//    static {
//        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "address_info_schema"));
//        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("deviceType");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "DeviceType"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("deviceID");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "DeviceID"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
