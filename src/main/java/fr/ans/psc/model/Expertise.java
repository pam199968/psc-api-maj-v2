package fr.ans.psc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Expertise
 */
public class Expertise   {
  @JsonProperty("expertiseId")
  private String expertiseId;

  @JsonProperty("typeCode")
  private String typeCode;

  @JsonProperty("code")
  private String code;

  public Expertise expertiseId(String expertiseId) {
    this.expertiseId = expertiseId;
    return this;
  }

  /**
   * Get expertiseId
   * @return expertiseId
  */
  @ApiModelProperty(value = "")

@Size(min = 1) 
  public String getExpertiseId() {
    return expertiseId;
  }

  public void setExpertiseId(String expertiseId) {
    this.expertiseId = expertiseId;
  }

  public Expertise typeCode(String typeCode) {
    this.typeCode = typeCode;
    return this;
  }

  /**
   * Get typeCode
   * @return typeCode
  */
  @ApiModelProperty(value = "")

@Size(min = 1) 
  public String getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }

  public Expertise code(String code) {
    this.code = code;
    return this;
  }

  /**
   * Get code
   * @return code
  */
  @ApiModelProperty(value = "")

@Size(min = 1) 
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Expertise expertise = (Expertise) o;
    return Objects.equals(this.expertiseId, expertise.expertiseId) &&
        Objects.equals(this.typeCode, expertise.typeCode) &&
        Objects.equals(this.code, expertise.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(expertiseId, typeCode, code);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Expertise {\n");
    
    sb.append("    expertiseId: ").append(toIndentedString(expertiseId)).append("\n");
    sb.append("    typeCode: ").append(toIndentedString(typeCode)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
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

