package ledgerserver.jpadao.convert;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import model.user.RoleType;

@Converter(autoApply = true)
public class RoleTypeAttributeConverter implements AttributeConverter<RoleType, String> {

    @Override
    public String convertToDatabaseColumn(RoleType attribute) {
        return attribute.name();
    }

    @Override
    public RoleType convertToEntityAttribute(String dbData) {
        return RoleType.Of(dbData);
    }

}
