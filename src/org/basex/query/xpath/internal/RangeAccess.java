package org.basex.query.xpath.internal;

import static org.basex.query.xpath.XPText.*;
import java.io.IOException;
import org.basex.BaseX;
import org.basex.data.Serializer;
import org.basex.index.IndexIterator;
import org.basex.index.RangeToken;
import org.basex.query.xpath.XPContext;
import org.basex.query.xpath.item.Nod;
import org.basex.util.Token;

/**
 * This index class retrieves range values from the index.
 * 
 * @author Workgroup DBIS, University of Konstanz 2005-08, ISC License
 * @author Christian Gruen
 */
public final class RangeAccess extends InternalExpr {
  /** Index type. */
  private final RangeToken ind;

  /**
   * Constructor.
   * @param i index terms
   */
  public RangeAccess(final RangeToken i) {
    ind = i;
  }

  @Override
  public Nod eval(final XPContext ctx) {
    final IndexIterator it = ctx.item.data.ids(ind);
    final int[] ids = new int[it.size()];
    int i = 0;
    while(it.more()) ids[i++] = it.next();
    ctx.item = new Nod(ids, ctx);
    return ctx.item;
  }

  @Override
  public void plan(final Serializer ser) throws IOException {
    ser.emptyElement(this, TYPE, Token.token(ind.type.toString()),
        MIN, Token.token(ind.min), MAX, Token.token(ind.max));
  }

  @Override
  public String color() {
    return "CC99FF";
  }

  @Override
  public String toString() {
    return BaseX.info("%(%, %-%)", name(), ind.type, ind.min, ind.max);
  }
}
