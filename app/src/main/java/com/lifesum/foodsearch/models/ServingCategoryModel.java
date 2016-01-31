package com.lifesum.foodsearch.models;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by PavlosPT13.
 * Class that represents a Serving Category.
 * (Did not have the time to incorporate this kind of information somewhere on the Design of the app)
 */
@JsonObject
public class ServingCategoryModel {

    @JsonField(name = JsonProperties.SC_CREATED)
    private long created;

    @JsonField(name = JsonProperties.SC_LINEAR_SIZE)
    private int linearSize;

    @JsonField(name = JsonProperties.SC_LINEAR_QUANTITY)
    private int linearQuantity;

    @JsonField(name = JsonProperties.SC_LAST_UPDATED)
    private long lastUpdated;

    @JsonField(name = JsonProperties.SC_DEFAULT_SIZE)
    private int defaultSize;

    @JsonField(name = JsonProperties.SC_OID)
    private int oid;

    @JsonField(name = JsonProperties.SC_SOURCE)
    private String source;


    @JsonField(name = JsonProperties.SC_USE_MEDIAN)
    private int useMedian;

    @JsonField(name = JsonProperties.NAME)
    private String name;

    @JsonField(name = JsonProperties.NAME_DA)
    private String nameDA;

    @JsonField(name = JsonProperties.NAME_DE)
    private String nameDE;

    @JsonField(name = JsonProperties.NAME_EN)
    private String nameEN;

    @JsonField(name = JsonProperties.NAME_ES)
    private String nameES;

    @JsonField(name = JsonProperties.NAME_FR)
    private String nameFR;

    @JsonField(name = JsonProperties.NAME_IT)
    private String nameIT;

    @JsonField(name = JsonProperties.NAME_NL)
    private String nameNL;

    @JsonField(name = JsonProperties.NAME_NO)
    private String nameNO;

    @JsonField(name = JsonProperties.NAME_PL)
    private String namePL;

    @JsonField(name = JsonProperties.NAME_PT)
    private String namePT;

    @JsonField(name = JsonProperties.NAME_RU)
    private String nameRU;

    @JsonField(name = JsonProperties.NAME_SV)
    private String nameSV;

    public ServingCategoryModel() {
    }

    public ServingCategoryModel(long created, int linearSize, int linearQuantity,
                                long lastUpdated, int defaultSize, int oid, String source,
                                int useMedian, String name, String nameDA, String nameDE,
                                String nameEN, String nameES, String nameFR, String nameIT,
                                String nameNL, String nameNO, String namePL, String namePT,
                                String nameRU, String nameSV) {
        this.created = created;
        this.linearSize = linearSize;
        this.linearQuantity = linearQuantity;
        this.lastUpdated = lastUpdated;
        this.defaultSize = defaultSize;
        this.oid = oid;
        this.source = source;
        this.useMedian = useMedian;
        this.name = name;
        this.nameDA = nameDA;
        this.nameDE = nameDE;
        this.nameEN = nameEN;
        this.nameES = nameES;
        this.nameFR = nameFR;
        this.nameIT = nameIT;
        this.nameNL = nameNL;
        this.nameNO = nameNO;
        this.namePL = namePL;
        this.namePT = namePT;
        this.nameRU = nameRU;
        this.nameSV = nameSV;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public int getLinearSize() {
        return linearSize;
    }

    public void setLinearSize(int linearSize) {
        this.linearSize = linearSize;
    }

    public int getLinearQuantity() {
        return linearQuantity;
    }

    public void setLinearQuantity(int linearQuantity) {
        this.linearQuantity = linearQuantity;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getDefaultSize() {
        return defaultSize;
    }

    public void setDefaultSize(int defaultSize) {
        this.defaultSize = defaultSize;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getUseMedian() {
        return useMedian;
    }

    public void setUseMedian(int useMedian) {
        this.useMedian = useMedian;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameDA() {
        return nameDA;
    }

    public void setNameDA(String nameDA) {
        this.nameDA = nameDA;
    }

    public String getNameDE() {
        return nameDE;
    }

    public void setNameDE(String nameDE) {
        this.nameDE = nameDE;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public String getNameES() {
        return nameES;
    }

    public void setNameES(String nameES) {
        this.nameES = nameES;
    }

    public String getNameFR() {
        return nameFR;
    }

    public void setNameFR(String nameFR) {
        this.nameFR = nameFR;
    }

    public String getNameIT() {
        return nameIT;
    }

    public void setNameIT(String nameIT) {
        this.nameIT = nameIT;
    }

    public String getNameNL() {
        return nameNL;
    }

    public void setNameNL(String nameNL) {
        this.nameNL = nameNL;
    }

    public String getNameNO() {
        return nameNO;
    }

    public void setNameNO(String nameNO) {
        this.nameNO = nameNO;
    }

    public String getNamePL() {
        return namePL;
    }

    public void setNamePL(String namePL) {
        this.namePL = namePL;
    }

    public String getNamePT() {
        return namePT;
    }

    public void setNamePT(String namePT) {
        this.namePT = namePT;
    }

    public String getNameRU() {
        return nameRU;
    }

    public void setNameRU(String nameRU) {
        this.nameRU = nameRU;
    }

    public String getNameSV() {
        return nameSV;
    }

    public void setNameSV(String nameSV) {
        this.nameSV = nameSV;
    }
}
