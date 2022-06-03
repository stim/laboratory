package nl.tbvh.lab.experiment.builders

class CelestialBody


class Planet(val name: String, val position: Int, val radius: Double, val satellites: List<CelestialBody>, val aphelion: Double, val perihelion: Double) {
}
