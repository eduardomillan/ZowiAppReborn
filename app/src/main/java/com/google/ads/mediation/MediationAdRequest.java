package com.google.ads.mediation;

import android.location.Location;
import com.google.ads.AdRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class MediationAdRequest {
    private final Date zzaT;
    private final AdRequest.Gender zzaU;
    private final Set<String> zzaV;
    private final boolean zzaW;
    private final Location zzaX;

    public MediationAdRequest(Date birthday, AdRequest.Gender gender, Set<String> keywords, boolean isTesting, Location location) {
        this.zzaT = birthday;
        this.zzaU = gender;
        this.zzaV = keywords;
        this.zzaW = isTesting;
        this.zzaX = location;
    }

    public Integer getAgeInYears() {
        if (this.zzaT == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar.setTime(this.zzaT);
        Integer numValueOf = Integer.valueOf(calendar2.get(1) - calendar.get(1));
        return (calendar2.get(2) < calendar.get(2) || (calendar2.get(2) == calendar.get(2) && calendar2.get(5) < calendar.get(5))) ? Integer.valueOf(numValueOf.intValue() - 1) : numValueOf;
    }

    public Date getBirthday() {
        return this.zzaT;
    }

    public AdRequest.Gender getGender() {
        return this.zzaU;
    }

    public Set<String> getKeywords() {
        return this.zzaV;
    }

    public Location getLocation() {
        return this.zzaX;
    }

    public boolean isTesting() {
        return this.zzaW;
    }
}
