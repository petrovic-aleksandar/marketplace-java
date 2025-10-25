package me.aco.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import me.aco.dto.TransferReq;
import me.aco.dto.TransferResp;
import me.aco.enums.TransferType;
import me.aco.model.Item;
import me.aco.model.Transfer;
import me.aco.model.User;

@Stateless
public class TransferService {

	@Inject
	private EntityManager em;

	public List<TransferResp> getByUser(User user) {
		List<Transfer> transfers = em.createNamedQuery(Transfer.getByUser, Transfer.class).setParameter("user", user)
				.getResultList();
		List<TransferResp> resp = new ArrayList<>();
		transfers.forEach(x -> resp.add(new TransferResp(x)));
		return resp;
	}

	public Transfer addPayment(TransferReq req, User user) {
		Transfer t = new Transfer();
		t.setAmount(req.getAmount());
		t.setType(TransferType.Payment);
		t.setCreatedAt(LocalDateTime.now());
		user.setBalance(user.getBalance() + req.getAmount());
		user = em.merge(user);
		t.setSeller(user);
		em.persist(t);
		return t;
	}

	public Transfer addWithdrawal(TransferReq req, User user) {
		Transfer t = new Transfer();
		t.setAmount(req.getAmount());
		t.setType(TransferType.Witdrawal);
		t.setCreatedAt(LocalDateTime.now());
		user.setBalance(user.getBalance() - req.getAmount());
		user = em.merge(user);
		t.setBuyer(user);
		em.persist(t);
		return t;
	}
	
	public Transfer addPurchase(TransferReq req, User buyer, User seller, Item item) {
		Transfer t = new Transfer();
		t.setAmount(req.getAmount());
		t.setType(TransferType.Purchase);
		t.setCreatedAt(LocalDateTime.now());
		buyer.setBalance(buyer.getBalance() - req.getAmount());
		buyer = em.merge(buyer);
		seller.setBalance(seller.getBalance() - req.getAmount());
		seller = em.merge(seller);
		item.setSeller(buyer);
		item.setActive(false);
		item = em.merge(item);
		t.setBuyer(buyer);
		t.setSeller(seller);
		t.setItem(item);
		em.persist(t);
		return t;
	}

}
