import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        // Handle the case where the timestamp is missing or null
        if (node == null || node.isNull()) {
            return null;  // Return null or a default value
        }

        // If the node is not null, process the timestamp value
        if (node.isTextual()) {
            String timestampStr = node.asText();
            try {
                return LocalDateTime.parse(timestampStr, formatter);
            } catch (Exception e) {
                return null;  // Handle the error or set a default value
            }
        }

        // Handle any other cases if necessary (like missing or invalid format)
        return null;
    }
}
