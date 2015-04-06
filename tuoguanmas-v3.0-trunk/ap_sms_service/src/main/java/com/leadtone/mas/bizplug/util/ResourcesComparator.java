/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.mas.bizplug.util;

import com.leadtone.mas.bizplug.security.bean.Resources;
import java.util.Comparator;

/**
 *
 * @author blueskybluesea
 */
public class ResourcesComparator implements Comparator<Resources> {

    @Override
    public int compare(Resources r1, Resources r2) {
        if(r1.getOrderNumber() > r2.getOrderNumber()) {
            return 1;
        } 
        if(r1.getOrderNumber() == r2.getOrderNumber()) {
            return 0;
        } 
        return -1;
    }    
}
