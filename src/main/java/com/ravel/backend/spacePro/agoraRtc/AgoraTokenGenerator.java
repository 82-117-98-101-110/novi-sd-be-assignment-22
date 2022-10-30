package com.ravel.backend.spacePro.agoraRtc;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class AgoraTokenGenerator {


    public String generateAgoraToken(String channelName, String userAccount) {
        String result = createAgoraVoiceToken(channelName, userAccount);
        return result;
    }



    private String createAgoraVoiceToken(String channelName, String userAccount) {
        String appId = "ff90a02189ee44ac841dc640e712870f";
        String appCertificate = "344925faf9a34ab1944262726316e4f4";
        int expirationTimeInSeconds = 36000;

        RtcTokenBuilder token = new RtcTokenBuilder();
        int timestamp = (int)(System.currentTimeMillis() / 1000 + expirationTimeInSeconds);
        String result = token.buildTokenWithUserAccount(appId, appCertificate,
                channelName, userAccount, RtcTokenBuilder.Role.Role_Publisher, timestamp);
        return result;
    }


    private static void TokenBuilders() {
         String appId = "ff90a02189ee44ac841dc640e712870f";
         String appCertificate = "344925faf9a34ab1944262726316e4f4";
         String channelName = "ravel_dev-znyCLVeqESCW";
         String userAccount = "68d533f1-c49c-41f5-a2d2-6100e87b24dc";
//         int uid = 2082341273;
         int expirationTimeInSeconds = 3600;


        RtcTokenBuilder token = new RtcTokenBuilder();
        int timestamp = (int)(System.currentTimeMillis() / 1000 + expirationTimeInSeconds);
        String result = token.buildTokenWithUserAccount(appId, appCertificate,
                channelName, userAccount, RtcTokenBuilder.Role.Role_Publisher, timestamp);
        System.out.println("channelName: " + channelName);
        System.out.println(("UserAccount: " + userAccount));
        System.out.println(result);

//        result = token.buildTokenWithUid(appId, appCertificate,
//                channelName, uid, RtcTokenBuilder.Role.Role_Publisher, timestamp);
//        System.out.println(result);
    }


}
