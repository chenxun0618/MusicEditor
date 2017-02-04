package cs3500.music.model;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

/**
 * to represent a musical note that has a pitch, a starting index of the note
 * and a number beats that the note is sustained
 */
public class Note implements Comparable<Note> {
  private Pitch pitch;
  private int head;
  private int sustain;
  private int instrument;
  private int volume;

  /**
   * checks the parameters of the note
   * @throws IllegalArgumentException if sustain or head is negative
   */
  private void checkParameters() {
    if(this.sustain < -1) {
      throw new IllegalArgumentException("Invalid sustain");
    }
    if(this.head < 0) {
      throw new IllegalArgumentException("Invalid head");
    }
    if(this.instrument < 0) {
      throw new IllegalArgumentException("Invalid instrument");
    }
    if(this.volume < 0) {
      throw new IllegalArgumentException("Invalid volumn");
    }
  }

  //default constructor
  public Note() {
    this.pitch = new Pitch();
    this.head = 0;
    this.sustain = 3;
    this.instrument = 1;
    this.volume = 100;
  }

  /**
   * @param pitch the pitch of the note
   * @param head the starting beat index of the note
   * @param sustain the beats that the note is sustained
   */
  public Note(Pitch pitch, int head, int sustain) {
    this.pitch = pitch;
    this.head = head;
    this.sustain = sustain;
    this.instrument = 1;
    this.volume = 100;
    this.checkParameters();
  }

  /**
   * @param pitch the pitch of the note
   * @param head the starting beat index of the note
   * @param sustain the beats that the note is sustained
   */
  public Note(int head, int sustain, int instrument, Pitch pitch, int volume) {
    this.head = head;
    this.sustain = sustain;
    this.pitch = pitch;
    this.instrument = instrument;
    this.volume = volume;
    this.checkParameters();
  }

  @Override
  public int compareTo(Note that) {
    if (this.pitch.equals(that.pitch)) {
      return this.head - that.head;
    }
    else {
      return this.pitch.compareTo(that.pitch);
    }

  }

  /**
   * checks if the note is played at the given beat
   * @param beat a beat
   * @return true if this note is played at the given beat
   */
  boolean playedAt(int beat) {
    return beat >= head && beat <= head + sustain;
  }

  @Override
  public boolean equals(Object that) {
    return this == that || that instanceof Note &&
            ((Note) that).pitch.equals(this.pitch) &&
            ((Note) that).head == this.head &&
            ((Note) that).sustain == this.sustain;

  }

  @Getter
  public Pitch getPitch() {
    return new Pitch(pitch);
  }

  @Setter
  public void setPitch(int pitchVal) {
    this.pitch = Pitch.buildPitch(pitchVal);
  }

  @Getter
  public int getSustain() {
    return sustain;
  }

  @Setter
  public void setSustain(int sustain) {
    this.sustain = sustain;
  }

  @Getter
  public int getHead() {
    return head;
  }

  @Setter
  public void setHead(int head) {
    this.head = head;
  }

  @Getter
  public int getInstrument() { return instrument; }

  @Setter
  public void setInstrument(int instrument) {
    this.instrument = instrument;
  }

  @Getter
  public int getVolume() { return volume; }

  @Setter
  public void setVolume(int volume) {
    this.volume = volume;
  }
}
