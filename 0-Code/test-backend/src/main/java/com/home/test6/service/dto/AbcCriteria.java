package com.home.test6.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Abc entity. This class is used in AbcResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /abcs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AbcCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter aaa;

    private StringFilter bbb;

    public AbcCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAaa() {
        return aaa;
    }

    public void setAaa(StringFilter aaa) {
        this.aaa = aaa;
    }

    public StringFilter getBbb() {
        return bbb;
    }

    public void setBbb(StringFilter bbb) {
        this.bbb = bbb;
    }

    @Override
    public String toString() {
        return "AbcCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (aaa != null ? "aaa=" + aaa + ", " : "") +
                (bbb != null ? "bbb=" + bbb + ", " : "") +
            "}";
    }

}
