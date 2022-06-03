package nl.tbvh.lab.experiment.builders;

public interface PlanetBuilder {

    // PlanetBuilder name(String name);
    //
    // PlanetBuilder position(Integer val);
    //
    // PlanetBuilder radius(Double val);
    //
    // PlanetBuilder satellites(List<CelestialBody> val);
    //
    // PlanetBuilder distance(Double val);

    // a, p, r, s, d
    // ap, ar, as, ad, pr, ps, ps, rs, rd, sd
    // apr, aps, apd, ars, ard, asd, prs, prd, psd, rsd
    // aprs, aprd, apsd, arsd, prsd
    // aprsd

    // a, p, r
    // ap, ar, pr
    // apr

    static PlanetWithoutNamePositionRadius builder() {
        return null;
    }

    interface PlanetComplete extends PlanetBuilder {
        Planet build();
    }

    interface PlanetWithoutPosition<T extends PlanetBuilder> extends PlanetBuilder {
        T position(Integer val);
    }

    interface PlanetWithoutName<T extends PlanetBuilder> extends PlanetBuilder {
        T name(String name);
    }

    interface PlanetWithoutRadius<T extends PlanetBuilder> extends PlanetBuilder {
        T radius(Double name);
    }

    interface PlanetWithoutNamePosition extends PlanetWithoutName<PlanetWithoutPosition<PlanetComplete>>, PlanetWithoutPosition<PlanetWithoutName<PlanetComplete>> {
    }

    interface PlanetWithoutNameRadius extends PlanetWithoutName<PlanetWithoutRadius<PlanetComplete>>, PlanetWithoutRadius<PlanetWithoutName<PlanetComplete>> {
    }

    interface PlanetWithoutPositionRadius extends PlanetWithoutPosition<PlanetWithoutRadius<PlanetComplete>>, PlanetWithoutRadius<PlanetWithoutPosition<PlanetComplete>> {
    }

    interface PlanetWithoutNamePositionRadius
            extends PlanetWithoutName<PlanetWithoutPositionRadius>, PlanetWithoutPosition<PlanetWithoutNameRadius>, PlanetWithoutRadius<PlanetWithoutPositionRadius> {
    }
}
