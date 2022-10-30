package com.ravel.backend.spacePro.agoraRtc;

public interface PackableEx extends Packable {
    void unmarshal(ByteBuf in);
}