package com.jemmy.user.config.security;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import java.io.IOException;

@JsonSerialize(using = SelfOAuthExceptionSerializer.class)
public class SelfOAuthException extends OAuth2Exception {
    public SelfOAuthException(String msg) {
        super(msg);
    }


}
class SelfOAuthExceptionSerializer extends StdSerializer<SelfOAuthException>{

    protected SelfOAuthExceptionSerializer() {
        super(SelfOAuthException.class);
    }

    @Override
    public void serialize(SelfOAuthException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("code",1);
        gen.writeStringField("msg", value.getMessage());
        gen.writeObjectField("data", null);
        gen.writeEndObject();
    }
}