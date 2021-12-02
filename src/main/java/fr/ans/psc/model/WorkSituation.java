package fr.ans.psc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * WorkSituation
 */
public class WorkSituation   {
  @JsonProperty("situId")
  private String situId;

  @JsonProperty("modeCode")
  private String modeCode;

  @JsonProperty("activitySectorCode")
  private String activitySectorCode;

  @JsonProperty("pharmacistTableSectionCode")
  private String pharmacistTableSectionCode;

  @JsonProperty("roleCode")
  private String roleCode;

  @JsonProperty("structures")
  @Valid
  private List<Structure> structures = null;

  public WorkSituation situId(String situId) {
    this.situId = situId;
    return this;
  }

  /**
   * Get situId
   * @return situId
  */
  @ApiModelProperty(value = "")

@Size(min = 1) 
  public String getSituId() {
    return situId;
  }

  public void setSituId(String situId) {
    this.situId = situId;
  }

  public WorkSituation modeCode(String modeCode) {
    this.modeCode = modeCode;
    return this;
  }

  /**
   * Get modeCode
   * @return modeCode
  */
  @ApiModelProperty(value = "")

@Size(min = 1) 
  public String getModeCode() {
    return modeCode;
  }

  public void setModeCode(String modeCode) {
    this.modeCode = modeCode;
  }

  public WorkSituation activitySectorCode(String activitySectorCode) {
    this.activitySectorCode = activitySectorCode;
    return this;
  }

  /**
   * Get activitySectorCode
   * @return activitySectorCode
  */
  @ApiModelProperty(value = "")

@Size(min = 1) 
  public String getActivitySectorCode() {
    return activitySectorCode;
  }

  public void setActivitySectorCode(String activitySectorCode) {
    this.activitySectorCode = activitySectorCode;
  }

  public WorkSituation pharmacistTableSectionCode(String pharmacistTableSectionCode) {
    this.pharmacistTableSectionCode = pharmacistTableSectionCode;
    return this;
  }

  /**
   * Get pharmacistTableSectionCode
   * @return pharmacistTableSectionCode
  */
  @ApiModelProperty(value = "")

@Size(min = 1) 
  public String getPharmacistTableSectionCode() {
    return pharmacistTableSectionCode;
  }

  public void setPharmacistTableSectionCode(String pharmacistTableSectionCode) {
    this.pharmacistTableSectionCode = pharmacistTableSectionCode;
  }

  public WorkSituation roleCode(String roleCode) {
    this.roleCode = roleCode;
    return this;
  }

  /**
   * Get roleCode
   * @return roleCode
  */
  @ApiModelProperty(value = "")

@Size(min = 1) 
  public String getRoleCode() {
    return roleCode;
  }

  public void setRoleCode(String roleCode) {
    this.roleCode = roleCode;
  }

  public WorkSituation structures(List<Structure> structures) {
    this.structures = structures;
    return this;
  }

  public WorkSituation addStructuresItem(Structure structuresItem) {
    if (this.structures == null) {
      this.structures = new ArrayList<Structure>();
    }
    this.structures.add(structuresItem);
    return this;
  }

  /**
   * Get structures
   * @return structures
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<Structure> getStructures() {
    return structures;
  }

  public void setStructures(List<Structure> structures) {
    this.structures = structures;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WorkSituation workSituation = (WorkSituation) o;
    return Objects.equals(this.situId, workSituation.situId) &&
        Objects.equals(this.modeCode, workSituation.modeCode) &&
        Objects.equals(this.activitySectorCode, workSituation.activitySectorCode) &&
        Objects.equals(this.pharmacistTableSectionCode, workSituation.pharmacistTableSectionCode) &&
        Objects.equals(this.roleCode, workSituation.roleCode) &&
        Objects.equals(this.structures, workSituation.structures);
  }

  @Override
  public int hashCode() {
    return Objects.hash(situId, modeCode, activitySectorCode, pharmacistTableSectionCode, roleCode, structures);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkSituation {\n");
    
    sb.append("    situId: ").append(toIndentedString(situId)).append("\n");
    sb.append("    modeCode: ").append(toIndentedString(modeCode)).append("\n");
    sb.append("    activitySectorCode: ").append(toIndentedString(activitySectorCode)).append("\n");
    sb.append("    pharmacistTableSectionCode: ").append(toIndentedString(pharmacistTableSectionCode)).append("\n");
    sb.append("    roleCode: ").append(toIndentedString(roleCode)).append("\n");
    sb.append("    structures: ").append(toIndentedString(structures)).append("\n");
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

