/**
 * User_id_schema.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.changyuan.misc.model.bean;

public class User_id_schema  implements java.io.Serializable {
    private java.math.BigInteger userIDType;

    private java.lang.String MSISDN;

    private byte[] pseudoCode;

    public User_id_schema() {
    }

    public User_id_schema(
           java.math.BigInteger userIDType,
           java.lang.String MSISDN,
           byte[] pseudoCode) {
           this.userIDType = userIDType;
           this.MSISDN = MSISDN;
           this.pseudoCode = pseudoCode;
    }


    /**
     * Gets the userIDType value for this User_id_schema.
     * 
     * @return userIDType
     */
    public java.math.BigInteger getUserIDType() {
        return userIDType;
    }


    /**
     * Sets the userIDType value for this User_id_schema.
     * 
     * @param userIDType
     */
    public void setUserIDType(java.math.BigInteger userIDType) {
        this.userIDType = userIDType;
    }


    /**
     * Gets the MSISDN value for this User_id_schema.
     * 
     * @return MSISDN
     */
    public java.lang.String getMSISDN() {
        return MSISDN;
    }


    /**
     * Sets the MSISDN value for this User_id_schema.
     * 
     * @param MSISDN
     */
    public void setMSISDN(java.lang.String MSISDN) {
        this.MSISDN = MSISDN;
    }


    /**
     * Gets the pseudoCode value for this User_id_schema.
     * 
     * @return pseudoCode
     */
    public byte[] getPseudoCode() {
        return pseudoCode;
    }


    /**
     * Sets the pseudoCode value for this User_id_schema.
     * 
     * @param pseudoCode
     */
    public void setPseudoCode(byte[] pseudoCode) {
        this.pseudoCode = pseudoCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof User_id_schema)) return false;
        User_id_schema other = (User_id_schema) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.userIDType==null && other.getUserIDType()==null) || 
             (this.userIDType!=null &&
              this.userIDType.equals(other.getUserIDType()))) &&
            ((this.MSISDN==null && other.getMSISDN()==null) || 
             (this.MSISDN!=null &&
              this.MSISDN.equals(other.getMSISDN()))) &&
            ((this.pseudoCode==null && other.getPseudoCode()==null) || 
             (this.pseudoCode!=null &&
              java.util.Arrays.equals(this.pseudoCode, other.getPseudoCode())));
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
        if (getUserIDType() != null) {
            _hashCode += getUserIDType().hashCode();
        }
        if (getMSISDN() != null) {
            _hashCode += getMSISDN().hashCode();
        }
        if (getPseudoCode() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPseudoCode());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPseudoCode(), i);
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
//        new org.apache.axis.description.TypeDesc(User_id_schema.class, true);
//
//    static {
//        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "user_id_schema"));
//        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("userIDType");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "UserIDType"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("MSISDN");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "MSISDN"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("pseudoCode");
//        elemField.setXmlName(new javax.xml.namespace.QName("http://www.monternet.com/dsmp/schemas/", "PseudoCode"));
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
