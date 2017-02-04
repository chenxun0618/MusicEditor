package cs3500.music.model;

/**
 * to represent the names of the pitches
 */
public enum PitchName {
  C, D, E, F, G, A, B;

  //default constructor
  PitchName() { }

  //returns the value of the pitch name
  int getPitchNameValue() {
    switch (this) {
      case C : return 0;
      case D : return 2;
      case E : return 4;
      case F : return 5;
      case G : return 7;
      case A : return 9;
      case B : return 11;
      default: throw new IllegalArgumentException("Invalid pitch name");
    }
  }

  //returns the next pitch's name
  PitchName nextPitchName() {
    switch (this) {
      case C : return D;
      case D : return E;
      case E : return F;
      case F : return G;
      case G : return A;
      case A : return B;
      case B : return C;
      default: throw new IllegalArgumentException("Invalid pitch name");
    }
  }

  /**
   * builds a pitch name with the value of the pitch name
   * @param i value of the pitch name
   * @return a PitchName
   */
  static PitchName buildPitchName(int i) {
    switch (i) {
      case 0 : return C;
      case 2 : return D;
      case 4 : return E;
      case 5 : return F;
      case 7 : return G;
      case 9 : return A;
      case 11 : return B;
      default: throw new IllegalArgumentException("Invalid value");
    }
  }
}
