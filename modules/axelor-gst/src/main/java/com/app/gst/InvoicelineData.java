package com.app.gst;

import java.math.BigDecimal;

import com.axelor.gst.db.Invoiceline;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

public class InvoicelineData {

  public void setInvoiceLineData(ActionRequest request, ActionResponse response) {
    Invoiceline inlinevoice = request.getContext().asType(Invoiceline.class);

    java.math.BigDecimal netAmount;
    double price = inlinevoice.getPrice().doubleValue();
    double qty = inlinevoice.getQty().doubleValue();
    Double netPrice = price * qty;
    netAmount=new BigDecimal(netPrice.toString());    
  }
}
