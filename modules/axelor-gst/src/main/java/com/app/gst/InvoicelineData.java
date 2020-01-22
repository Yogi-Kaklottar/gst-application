package com.app.gst;

import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Invoiceline;
import com.axelor.gst.db.repo.InvoicelineRepository;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import java.math.BigDecimal;
import java.util.ArrayList;

public class InvoicelineData {

  public void setInvoiceLineData(ActionRequest request, ActionResponse response) {
    Invoice invoice= request.getContext().asType(Invoice.class);
    double netamount=0,netigst=0,netcsgst=0,netsgst=0,grossamount=0;
    ArrayList<Invoiceline> il=(ArrayList<Invoiceline>)invoice.getInvoiceitem();
    
    for(Invoiceline invoiceline:il)
    {
    	netamount=netamount+invoiceline.getNetamount().doubleValue();
    	netigst=netigst+invoiceline.getIgst().doubleValue();
    	netcsgst=netcsgst+invoiceline.getCgst().doubleValue();
    	netsgst=netsgst+invoiceline.getSgst().doubleValue();
    	grossamount=grossamount+netamount+netcsgst+netigst+netsgst;
    	
    }
    
    invoice.setNetamount(new BigDecimal(netamount));
    invoice.setNetigst(new BigDecimal(netigst));
    invoice.setNetcsgst(new BigDecimal(netcsgst));
    invoice.setNetsgst(new BigDecimal(netsgst));
    invoice.setGrossamount(new BigDecimal(grossamount));    
    response.setValues(invoice); 
    
  }
}
