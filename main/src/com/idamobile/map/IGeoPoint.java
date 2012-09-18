package com.idamobile.map;

/**
 * Map points should implement this base interface to be eligible for clustering
 * 
 * @author colriot
 * @since Jul 10, 2012
 * 
 */
public interface IGeoPoint {
    public int getLat();

    public int getLng();
}
