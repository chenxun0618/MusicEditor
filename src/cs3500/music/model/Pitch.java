package cs3500.music.model;


import java.util.ArrayList;
import java.util.List;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

/**
 * to represent a pitch, with a pitch name, a sharp flag that indicates
 * if the pitch is sharp or not, a octave and a list of notes that are of this pitch
 */
public class Pitch implements Comparable<Pitch> {
  private PitchName name;
  private boolean sharp;
  private int octave;
  private List<Note> notes;

  //default constructor
  public Pitch() {
    this.name = PitchName.C;
    this.sharp = false;
    this.octave = 4;
    this.notes = new ArrayList<>();
  }

  public Pitch(Pitch p) {
    this.name = p.name;
    this.sharp = p.sharp;
    this.octave = p.octave;
    this.notes = p.notes;
  }

  /**
   * @param name name of the pitch
   * @param sharp sharp or not
   * @param octave octave of the pitch
   */
  public Pitch(PitchName name, boolean sharp, int octave) {
    if(octave < -1) {
      throw new IllegalArgumentException("Invalid octave");
    }
    this.name = name;
    this.sharp = sharp;
    this.octave = octave;
    this.notes = new ArrayList<>();
    this.checkPitch();
  }

  //checks for E and B, if they are sharp then converts them to F or C.
  private void checkPitch() {
    if (this.name == PitchName.E && this.sharp) {
      this.name = PitchName.F;
      this.sharp = false;
    }
    if (this.name == PitchName.B && this.sharp) {
      this.name = PitchName.C;
      this.octave += 1;
      this.sharp = false;
    }
  }

  @Override
  public int compareTo(Pitch that) {
    return this.getPitchValue() - that.getPitchValue();
  }

  //prints the pitch as a string
  public String printPitch() {
    StringBuilder result = new StringBuilder();
    if (this.sharp) {
      return result.append(this.name.toString()).append('#').append(octave).toString();
    }
    else {
      return result.append(this.name.toString()).append(octave).toString();
    }
  }

  /**
   * checks if the pitch is played at the given beat
   * @param beat a beat
   * @return true if this pitch is played at the given beat
   */
  boolean playedAt(int beat) {
    for (Note n : this.notes) {
      if (n.playedAt(beat)) {
        return true;
      }
    }
    return false;
  }

  //returns the value of this pitch
  public int getPitchValue() {
    int result = this.name.getPitchNameValue() + (octave + 1) * 12;
    if (sharp) {
      result += 1;
    }
    return result;
  }

  /**
   * builds a pitch with a pitch value
   * @param value a value that represents the pitch
   * @return a pitch
   */
  public static Pitch buildPitch(int value) {
    int octave = value / 12 - 1;
    int nameVal = value % 12;
    Pitch p = new Pitch();
    p.octave = octave;
    try {
      p.name = PitchName.buildPitchName(nameVal);
    }
    catch (IllegalArgumentException e) {
      p.name = PitchName.buildPitchName(nameVal - 1);
      p.sharp = true;
    }
    return p;
  }

  @Override
  public boolean equals(Object that) {
    return this == that || that instanceof Pitch &&
            ((Pitch) that).getPitchValue() == this.getPitchValue();

  }

  @Getter
  public List<Note> getNotes() {
    return new ArrayList<>(notes);
  }

  @Setter
  public void setNotes(List<Note> notes) {
    this.notes = notes;
  }
}
