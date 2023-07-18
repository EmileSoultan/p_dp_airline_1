package app.dto.account;


import app.entities.account.AirlineManager;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Deprecated
@Getter
@Setter
@NoArgsConstructor
@JsonTypeName(value = "manager")
public class AirlineManagerDTO extends AccountDTO {

    public AirlineManagerDTO(AirlineManager airlineManager) {
        super(airlineManager);
    }

    @Override
    public AirlineManager convertToEntity() {
        var airlineManager = new AirlineManager();
        airlineManager.setId(getId());
        airlineManager.setEmail(getEmail());
        airlineManager.setPassword(getPassword());
        airlineManager.setSecurityQuestion(getSecurityQuestion());
        airlineManager.setAnswerQuestion(getAnswerQuestion());
        airlineManager.setRoles(getRoles());
        return airlineManager;
    }
}
