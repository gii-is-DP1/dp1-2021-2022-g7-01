package samuraisword.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samuraisword.game.GameService;
import samuraisword.invitations.Invitation;
import samuraisword.invitations.InvitationService;
import samuraisword.samples.petclinic.user.UserService;


	
	@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
	public class InvitationServiceTests {
		
		@Autowired
		protected InvitationService inviService;
		protected UserService uService;
		protected GameService gService;
		
		
		@Test
		void shouldFindInvitationById() {
			Optional<Invitation> inv = this.inviService.findById(1);
			assertThat(inv.isEmpty());
			
			inv = this.inviService.findById(3);
			assertThat(inv.isEmpty()).isTrue();
		}
		
		
		@Test
		void shouldFindListInvitationByname() {
			Collection<Invitation> inv = this.inviService.findAllByUser("alefr99");
			assertThat(!inv.isEmpty());
			
			inv = this.inviService.findAllByUser("alefr99");
			assertThat(inv.isEmpty()).isTrue();
		}
		
		
//		@Test
//		@Transactional
//		void shouldSendInvitation() {
//			Collection<Invitation> antiListReq = this.inviService.findAllByUser("alfcadmor");
//			int previousCount = antiListReq.size();
//			User a=this.uService.findUser("alefr99").get();
//			User aa=this.uService.findUser("alfcadmor").get();
//			Game g=this.gService.findById(2).get();
//			this.inviService.sendInvitation(a, aa, g);
//			Collection<Invitation> ListReq = this.inviService.findAllByUser("alfcadmor");
//			int count = ListReq.size();
//			assertThat(count!=previousCount);
//			
//			antiListReq = this.inviService.findAllByUser("alfcadmor");
//			User a1=this.uService.findUser("alefr99").get();
//			User aa1=this.uService.findUser("alfcadmor").get();
//			Game g1=this.gService.findById(1).get();
//			this.inviService.sendInvitation(a1, aa1, g1);
//			ListReq = this.inviService.findAllByUser("alfcadmor");
//			assertThat(antiListReq).isEqualTo(ListReq);
//		}
		
		@Test
		@Transactional
		void shouldDeclineInvitation() {
			Optional<Invitation> inv=inviService.findById(1);
			
			this.inviService.declineInvitation(inv.get());
		
		}
		
//		@Test
//		@Transactional
//		void shouldAcceptInvitation() {
//			Invitation inv=inviService.findById(2).get();
//			
//			this.inviService.acceptInvitation(inv);
//		
//		}

}
