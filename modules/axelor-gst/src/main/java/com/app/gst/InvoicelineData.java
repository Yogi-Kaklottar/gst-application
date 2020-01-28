package com.app.gst;

import com.app.service.ServiceData;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Invoiceline;
import com.axelor.gst.db.Product;
import com.axelor.gst.db.repo.ProductRepository;
import com.axelor.meta.schema.actions.ActionView;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class InvoicelineData {

  @Inject ServiceData setData;

  public void setInvoiceData(ActionRequest request, ActionResponse response) {
    try {
      Invoice invoice = request.getContext().asType(Invoice.class);
      ArrayList<Invoiceline> il = (ArrayList<Invoiceline>) invoice.getInvoiceitemList();
      invoice = setData.setInvoiceDataService(invoice, il);
      response.setValues(invoice);

    } catch (Exception e) {
      // TODO: handle exception
      System.out.println("Method:setInvoiceData");
    }
  }

  public void setInvoiceLineData(ActionRequest request, ActionResponse response) {
    try {
      Invoiceline invoiceline = request.getContext().asType(Invoiceline.class);
      Invoice invoice = request.getContext().getParent().asType(Invoice.class);
      invoiceline = setData.setInvoicelineData(invoice, invoiceline);
      response.setValues(invoiceline);
    } catch (Exception e) {
      System.out.println("Method:setInvoiceLineData");
    }
  }

  public void setinvoiceDefaultfield(ActionRequest request, ActionResponse respons) {
    try {
      Invoice invoice = request.getContext().asType(Invoice.class);
      invoice = setData.setPartyData(invoice);
      respons.setValues(invoice);
    } catch (Exception e) {
      System.out.println("Method:setinvoiceDefaultfield");
    }
  }

  public void setInvoicelinePartyChange(ActionRequest request, ActionResponse respons) {
    try {
      Invoice invoice = request.getContext().asType(Invoice.class);
      if (invoice != null) {
        if (invoice.getInvoiceitemList().size() == 0) {
        } else {
          ArrayList<Invoiceline> il = (ArrayList<Invoiceline>) invoice.getInvoiceitemList();
          for (Invoiceline i : il) {
            i = setData.setPartyCompanyChangeData(invoice, i);
          }
          invoice = setData.setInvoiceDataService(invoice, il);
        }
      }
      respons.setValues(invoice);
    } catch (Exception e) {
      System.out.println("Method:setInvoicelinePartyChange");
    }
  }

  public void setCreateNewInvoice(ActionRequest request, ActionResponse respons) {

    try {
		      ArrayList<Invoiceline> invoicelinelist = new ArrayList<Invoiceline>();
		      Product pro;
		      List<Integer> list = (List<Integer>) request.getContext().get("_ids");
		      ArrayList<Long> arraylist = new ArrayList<Long>();
		      for (Integer i : list) {
		        arraylist.add((long) i);
		      }
		
		      ProductRepository p = new ProductRepository();
		
		      for (Long l : arraylist) {
		
		        pro = p.find(l);
		        System.out.println(pro);
		        Invoiceline il = new Invoiceline();
		        il.setItem(pro.getCode() + "" + pro.getName());
		        il.setHsbn(pro.getHsbn());
		        il.setPrice(pro.getSaleprice());
		        il.setProduct(pro);
		        il.setGstrate(pro.getGstrate());
		        invoicelinelist.add(il);
			      }
			      Invoice invoice = new Invoice();
			      invoice.setInvoiceitemList(invoicelinelist);
			      respons.setView(
			          ActionView.define("Invoice")
			              .model("com.axelor.gst.db.Invoice")
			              .add("form", "gst-invoice-form")
			              .context("invoiceitemList", invoicelinelist)
			              .map());
    } catch (Exception e) {
      // System.err.println("problem");
    }
  }
}
