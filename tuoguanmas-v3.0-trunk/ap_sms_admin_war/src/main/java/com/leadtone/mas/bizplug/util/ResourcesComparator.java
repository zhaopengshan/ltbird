package com.leadtone.mas.bizplug.util;

import java.util.Comparator;

import com.leadtone.mas.bizplug.security.bean.Resources;

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
