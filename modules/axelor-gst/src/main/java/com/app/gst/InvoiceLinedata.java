package com.app.gst;

import com.axelor.gst.db.Invoiceline;
import com.axelor.gst.db.repo.InvoicelineRepository;

public class InvoiceLinedata extends InvoicelineRepository {
  @Override
  public Invoiceline save(Invoiceline entity) {
    // TODO Auto-generated method stub
    return super.save(entity);
  }

  @Override
  public void persist(Invoiceline entity) {
    // TODO Auto-generated method stub
    super.persist(entity);
  }
}
