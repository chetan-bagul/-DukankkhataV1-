package com.dukankhata.subscription;
import com.fasterxml.jackson.databind.JsonNode; import com.fasterxml.jackson.databind.ObjectMapper; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service;
import java.io.IOException; import java.net.URI; import java.net.http.*; import java.nio.charset.StandardCharsets; import java.util.Base64;
@Service public class RazorpayClient {
 private final String keyId,keySecret; private final ObjectMapper mapper=new ObjectMapper();
 public RazorpayClient(@Value("${razorpay.key-id:}") String keyId,@Value("${razorpay.key-secret:}") String keySecret){this.keyId=keyId;this.keySecret=keySecret;}
 public String getKeyId(){return keyId;}
 public JsonNode createOrder(long amountPaise,String currency,String receipt){
  if(keyId.isBlank()||keySecret.isBlank()) throw new IllegalStateException("Razorpay is not configured. Set RAZORPAY_KEY_ID and RAZORPAY_KEY_SECRET.");
  try{String auth=Base64.getEncoder().encodeToString((keyId+":"+keySecret).getBytes(StandardCharsets.UTF_8)); String body=mapper.createObjectNode().put("amount",amountPaise).put("currency",currency).put("receipt",receipt).put("payment_capture",1).toString();
   HttpRequest req=HttpRequest.newBuilder(URI.create("https://api.razorpay.com/v1/orders")).header("Authorization","Basic "+auth).header("Content-Type","application/json").POST(HttpRequest.BodyPublishers.ofString(body)).build();
   HttpResponse<String> res=HttpClient.newHttpClient().send(req,HttpResponse.BodyHandlers.ofString()); if(res.statusCode()<200||res.statusCode()>=300) throw new IllegalStateException("Razorpay order creation failed"); return mapper.readTree(res.body());
  }catch(IOException e){throw new IllegalStateException("Unable to reach Razorpay",e);}catch(InterruptedException e){Thread.currentThread().interrupt();throw new IllegalStateException("Razorpay request interrupted",e);}}
 public boolean verifySignature(String orderId,String paymentId,String signature){return !keySecret.isBlank()&&constantTimeEquals(hmac(orderId+"|"+paymentId,keySecret),signature);}
 public boolean verifyWebhookSignature(String payload,String signature,String webhookSecret){return !webhookSecretBlank(webhookSecret)&&constantTimeEquals(hmac(payload,webhookSecret),signature);}
 private boolean webhookSecretBlank(String s){return s==null||s.isBlank();}
 private boolean constantTimeEquals(String a,String b){return java.security.MessageDigest.isEqual(a.getBytes(StandardCharsets.UTF_8),b.getBytes(StandardCharsets.UTF_8));}
 private String hmac(String payload,String secret){try{var mac=javax.crypto.Mac.getInstance("HmacSHA256");mac.init(new javax.crypto.spec.SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),"HmacSHA256"));byte[] out=mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));StringBuilder s=new StringBuilder();for(byte b:out)s.append(String.format("%02x",b));return s.toString();}catch(Exception e){throw new IllegalStateException("Signature verification failed",e);}}
}
