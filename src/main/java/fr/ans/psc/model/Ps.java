package fr.ans.psc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Ps
 */
@Document(collection = "ps")
public class Ps   {
  @JsonProperty("idType")
  private String idType;

  @JsonProperty("id")
  private String id;

  @JsonProperty("nationalId")
  @Id
  private String nationalId;

  @JsonProperty("lastName")
  private String lastName;

  @JsonProperty("firstName")
  private String firstName;

  @JsonProperty("dateOfBirth")
  private String dateOfBirth;

  @JsonProperty("birthAddressCode")
  private String birthAddressCode;

  @JsonProperty("birthCountryCode")
  private String birthCountryCode;

  @JsonProperty("birthAddress")
  private String birthAddress;

  @JsonProperty("genderCode")
  private String genderCode;

  @JsonProperty("phone")
  private String phone;

  @JsonProperty("email")
  private String email;

  @JsonProperty("salutationCode")
  private String salutationCode;

  @JsonProperty("professions")
  @Valid
  private List<Profession> professions = null;

  public Ps idType(String idType) {
    this.idType = idType;
    return this;
  }

  /**
   * Get idType
   * @return idType
  */
  @ApiModelProperty(value = "")

@Size(min = 1) 
  public String getIdType() {
    return idType;
  }

  public void setIdType(String idType) {
    this.idType = idType;
  }

  public Ps id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @ApiModelProperty(value = "")

@Size(min = 1) 
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Ps nationalId(String nationalId) {
    this.nationalId = nationalId;
    return this;
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

  public Ps lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Get lastName
   * @return lastName
  */
  @ApiModelProperty(value = "")

@Size(min = 1) 
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Ps firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * Get firstName
   * @return firstName
  */
  @ApiModelProperty(value = "")

@Size(min = 1) 
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public Ps dateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
    return this;
  }

  /**
   * Get dateOfBirth
   * @return dateOfBirth
  */
  @ApiModelProperty(value = "")

@Size(min = 1) 
  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public Ps birthAddressCode(String birthAddressCode) {
    this.birthAddressCode = birthAddressCode;
    return this;
  }

  /**
   * Get birthAddressCode
   * @return birthAddressCode
  */
  @ApiModelProperty(value = "")

@Size(min = 1) 
  public String getBirthAddressCode() {
    return birthAddressCode;
  }

  public void setBirthAddressCode(String birthAddressCode) {
    this.birthAddressCode = birthAddressCode;
  }

  public Ps birthCountryCode(String birthCountryCode) {
    this.birthCountryCode = birthCountryCode;
    return this;
  }

  /**
   * Get birthCountryCode
   * @return birthCountryCode
  */
  @ApiModelProperty(value = "")

@Size(min = 1) 
  public String getBirthCountryCode() {
    return birthCountryCode;
  }

  public void setBirthCountryCode(String birthCountryCode) {
    this.birthCountryCode = birthCountryCode;
  }

  public Ps birthAddress(String birthAddress) {
    this.birthAddress = birthAddress;
    return this;
  }

  /**
   * Get birthAddress
   * @return birthAddress
  */
  @ApiModelProperty(value = "")

@Size(min = 1) 
  public String getBirthAddress() {
    return birthAddress;
  }

  public void setBirthAddress(String birthAddress) {
    this.birthAddress = birthAddress;
  }

  public Ps genderCode(String genderCode) {
    this.genderCode = genderCode;
    return this;
  }

  /**
   * Get genderCode
   * @return genderCode
  */
  @ApiModelProperty(value = "")

@Size(min = 1) 
  public String getGenderCode() {
    return genderCode;
  }

  public void setGenderCode(String genderCode) {
    this.genderCode = genderCode;
  }

  public Ps phone(String phone) {
    this.phone = phone;
    return this;
  }

  /**
   * Get phone
   * @return phone
  */
  @ApiModelProperty(value = "")

@Size(min = 1) 
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Ps email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
  */
  @ApiModelProperty(value = "")

@Size(min = 1) 
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Ps salutationCode(String salutationCode) {
    this.salutationCode = salutationCode;
    return this;
  }

  /**
   * Get salutationCode
   * @return salutationCode
  */
  @ApiModelProperty(value = "")

@Size(min = 1) 
  public String getSalutationCode() {
    return salutationCode;
  }

  public void setSalutationCode(String salutationCode) {
    this.salutationCode = salutationCode;
  }

  public Ps professions(List<Profession> professions) {
    this.professions = professions;
    return this;
  }

  public Ps addProfessionsItem(Profession professionsItem) {
    if (this.professions == null) {
      this.professions = new ArrayList<Profession>();
    }
    this.professions.add(professionsItem);
    return this;
  }

  /**
   * Get professions
   * @return professions
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<Profession> getProfessions() {
    return professions;
  }

  public void setProfessions(List<Profession> professions) {
    this.professions = professions;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Ps ps = (Ps) o;
    return Objects.equals(this.idType, ps.idType) &&
        Objects.equals(this.id, ps.id) &&
        Objects.equals(this.nationalId, ps.nationalId) &&
        Objects.equals(this.lastName, ps.lastName) &&
        Objects.equals(this.firstName, ps.firstName) &&
        Objects.equals(this.dateOfBirth, ps.dateOfBirth) &&
        Objects.equals(this.birthAddressCode, ps.birthAddressCode) &&
        Objects.equals(this.birthCountryCode, ps.birthCountryCode) &&
        Objects.equals(this.birthAddress, ps.birthAddress) &&
        Objects.equals(this.genderCode, ps.genderCode) &&
        Objects.equals(this.phone, ps.phone) &&
        Objects.equals(this.email, ps.email) &&
        Objects.equals(this.salutationCode, ps.salutationCode) &&
        Objects.equals(this.professions, ps.professions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idType, id, nationalId, lastName, firstName, dateOfBirth, birthAddressCode, birthCountryCode, birthAddress, genderCode, phone, email, salutationCode, professions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Ps {\n");
    
    sb.append("    idType: ").append(toIndentedString(idType)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nationalId: ").append(toIndentedString(nationalId)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    dateOfBirth: ").append(toIndentedString(dateOfBirth)).append("\n");
    sb.append("    birthAddressCode: ").append(toIndentedString(birthAddressCode)).append("\n");
    sb.append("    birthCountryCode: ").append(toIndentedString(birthCountryCode)).append("\n");
    sb.append("    birthAddress: ").append(toIndentedString(birthAddress)).append("\n");
    sb.append("    genderCode: ").append(toIndentedString(genderCode)).append("\n");
    sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    salutationCode: ").append(toIndentedString(salutationCode)).append("\n");
    sb.append("    professions: ").append(toIndentedString(professions)).append("\n");
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

