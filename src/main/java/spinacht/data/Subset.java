package spinacht.data;

import java.util.HashSet;
import java.util.Collection;
import java.util.stream.Collectors;


public class Subset extends HashSet<Point> {

  public Subset() {
    super();
  }

  public Subset(Collection<Point> c) {
    super(c);
  }

}
