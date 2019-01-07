package com.lyonguyen.esky.logic.mapper;

import org.hashids.Hashids;

public class HashIdsWrap {

    private Hashids hashids;

    public HashIdsWrap(String salt, int length) {
        hashids = new Hashids(salt, length);
    }

    public HashIdsWrap(String salt, int length, String alphabet) {
        hashids = new Hashids(salt, length, alphabet);
    }

    public String encode(int id) {
        return hashids.encode(id);
    }

    public String encode(String id) {
        if(id == null) {
            return null;
        }
        return hashids.encode(Integer.parseInt(id));
    }

    public Integer decode(String id) {
        long[] longs = hashids.decode(id);
        if(longs.length == 0) {
            return null;
        }
        return (int) hashids.decode(id)[0];
    }
}
