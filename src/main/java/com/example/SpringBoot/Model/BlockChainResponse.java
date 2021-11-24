package com.example.SpringBoot.Model;

public class BlockChainResponse {

    public String hash;

    public String height;

    public long time;

    public long block_index;


    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getBlock_index() {
        return block_index;
    }

    public void setBlock_index(long block_index) {
        this.block_index = block_index;
    }

}
