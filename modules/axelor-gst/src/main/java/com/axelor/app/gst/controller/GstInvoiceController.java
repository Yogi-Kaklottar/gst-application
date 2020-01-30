package com.axelor.app.gst.controller;

import com.axelor.app.gst.invoice.service.GstInvoiceService;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.gst.db.Product;
import com.axelor.gst.db.repo.ProductRepository;
import com.axelor.inject.Beans;
import com.axelor.meta.schema.actions.ActionView;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class GstInvoiceController {
  @Inject GstInvoiceService gstInvoiceService;

  public void setInvoicePaid(ActionRequest request, ActionResponse response) {
    try {
      Invoice invoice = request.getContext().asType(Invoice.class);
      invoice.setStatus("Paid");
      response.setValues(invoice);
    } catch (Exception e) {

    }
  }

  public void setInvoiceCancel(ActionRequest request, ActionResponse response) {
    try {
      Invoice invoice = request.getContext().asType(Invoice.class);
      invoice.setStatus("Cancelled");
      response.setValues(invoice);
    } catch (Exception e) {
    }
  }

  public void setInvoicePatryDefault(ActionRequest request, ActionResponse response) {
    try {
      Invoice invoice = request.getContext().asType(Invoice.class);
      invoice = gstInvoiceService.setInvoicePartySelectDefault(invoice);
      response.setValues(invoice);
    } catch (Exception e) {
    }
  }

  public void setInvoiceComputation(ActionRequest request, ActionResponse response) {
    try {
      Invoice invoice = request.getContext().asType(Invoice.class);
      List<InvoiceLine> invoiceLineList = (List<InvoiceLine>) invoice.getInvoiceItemList();
      invoice = gstInvoiceService.setInvoiceComputation(invoice, invoiceLineList);
      response.setValues(invoice);

    } catch (Exception e) {
      // TODO: handle exception
      // System.out.println("Method:setInvoiceComputation");
    }
  }

  public void setInvoiceComputationComapanyPartyChange(
      ActionRequest request, ActionResponse respons) {
    try {
      Invoice invoice = request.getContext().asType(Invoice.class);
      if (invoice != null) {
        if (invoice.getInvoiceItemList().size() == 0) {
        } else {
          List<InvoiceLine> invoiceLineList = (List<InvoiceLine>) invoice.getInvoiceItemList();
          for (InvoiceLine invoiceLine : invoiceLineList) {
            invoiceLine = gstInvoiceService.setComputationPartyCompanyChange(invoice, invoiceLine);
          }

          invoice = gstInvoiceService.setInvoiceComputation(invoice, invoiceLineList);
        }
      }
      respons.setValues(invoice);
    } catch (Exception e) {
      // System.out.println("Method:setInvoicelinePartyChange");
    }
  }

  public void setInvoicelinePartyChange(ActionRequest request, ActionResponse respons) {
    try {
      Invoice invoice = request.getContext().asType(Invoice.class);
      if (invoice != null) {
        if (invoice.getInvoiceItemList().size() == 0) {
        } else {
          ArrayList<InvoiceLine> invoiceLineList =
              (ArrayList<InvoiceLine>) invoice.getInvoiceItemList();
          for (InvoiceLine invoiceLine : invoiceLineList) {
            invoiceLine = gstInvoiceService.setComputationPartyCompanyChange(invoice, invoiceLine);
          }
          invoice = gstInvoiceService.setInvoiceComputation(invoice, invoiceLineList);
        }
      }
      respons.setValues(invoice);
    } catch (Exception e) {
      // System.out.println("Method:setInvoicelinePartyChange");
    }
  }

  public void setCreateNewInvoice(ActionRequest request, ActionResponse respons) {

    try {
      ArrayList<InvoiceLine> invoiceLinelist = new ArrayList<InvoiceLine>();
      Product product;
      List<Integer> list = (List<Integer>) request.getContext().get("_ids");
      ArrayList<Long> arraylist = new ArrayList<Long>();
      for (Integer i : list) {
        arraylist.add((long) i);
      }

      for (Long l : arraylist) {
        product = Beans.get(ProductRepository.class).find(l);
        // System.out.println(product);
        InvoiceLine invoiceLine = new InvoiceLine();
        invoiceLine.setItem(product.getCode() + "" + product.getName());
        invoiceLine.setHsbn(product.getHsbn());
        invoiceLine.setPrice(product.getSalePrice());
        invoiceLine.setProduct(product);
        invoiceLine.setGstRate(product.getGstRate());
        invoiceLinelist.add(invoiceLine);
      }
      Invoice invoice = new Invoice();
      invoice.setInvoiceItemList(invoiceLinelist);
      respons.setView(
          ActionView.define("Invoice")
              .model("com.axelor.gst.db.Invoice")
              .add("form", "gst-invoice-form")
              .context("_invoiceItemList", invoiceLinelist)
              .map());
    } catch (Exception e) {
      // System.err.println("problem");
    }
  }

  public void setAsBolleanShippingAddressSet(ActionRequest request, ActionResponse respons) {

    try {

      Invoice invoice = request.getContext().asType(Invoice.class);
      invoice = gstInvoiceService.setAsBooleanValueShippingAddress(invoice);
      respons.setValues(invoice);
    } catch (Exception e) {
      System.out.println("problem");
    }
  }
}
