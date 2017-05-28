package spinacht.subclu;

import spinacht.common.Params;
import spinacht.data.Clustering;


public class SUBCLU {

  public static Clustering go(Params params) {
    Trie trie = new Trie(params);
    while (trie.extend());
    return trie;
  }

}
