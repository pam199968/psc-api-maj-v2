package fr.ans.psc.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.*;

/**
 * Mapping identifier to Ps
 */
@ApiModel(description = "Mapping identifier to Ps")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-12-07T17:22:10.562370300+01:00[Europe/Paris]")
public class PsRef   {

  @Id
  private String _id;

  @JsonProperty("nationalIdRef")
  private String nationalIdRef;

  @JsonProperty("nationalId")
  private String nationalId;

  @JsonProperty("activated")
  private Long activated;

  @JsonProperty("deactivated")
  private Long deactivated;

  public PsRef() {
  }

  public PsRef(String nationalIdRef, String nationalId, Long activated) {
    this.nationalIdRef = nationalIdRef;
    this.nationalId = nationalId;
    this.activated = activated;
  }

  /**
   * Get nationalIdRef
   * @return nationalIdRef
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull
@Size(min = 1) 
  public String getNationalIdRef() {
    return nationalIdRef;
  }

  public void setNationalIdRef(String nationalIdRef) {
    this.nationalIdRef = nationalIdRef;
  }

  /**
   * Get nationalId
   * @return nationalId
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull
@Size(min = 1) 
  public String getNationalId() {
    return nationalId;
  }

  public void setNationalId(String nationalId) {
    this.nationalId = nationalId;
  }

  /**
   * Get activated
   * @return activated
  */
  @ApiModelProperty(value = "")
  public Long getActivated() {
    return activated;
  }

  public void setActivated(Long activated) {
    this.activated = activated;
  }
  /**
   * Get deactivated
   * @return deactivated
  */
  @ApiModelProperty(value = "")
  public Long getDeactivated() {
    return deactivated;
  }

  public void setDeactivated(Long deactivated) {
    this.deactivated = deactivated;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PsRef psRef = (PsRef) o;
    return Objects.equals(this.nationalIdRef, psRef.nationalIdRef) &&
        Objects.equals(this.nationalId, psRef.nationalId) &&
        Objects.equals(this.activated, psRef.activated) &&
        Objects.equals(this.deactivated, psRef.deactivated);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nationalIdRef, nationalId, activated, deactivated);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PsRef {\n");
    
    sb.append("    nationalIdRef: ").append(toIndentedString(nationalIdRef)).append("\n");
    sb.append("    nationalId: ").append(toIndentedString(nationalId)).append("\n");
    sb.append("    activated: ").append(toIndentedString(activated)).append("\n");
    sb.append("    deactivated: ").append(toIndentedString(deactivated)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

