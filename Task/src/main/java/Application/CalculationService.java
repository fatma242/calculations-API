package Application;

import javax.ejb.Stateless;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.persistence.*;
import java.util.List;

import ejbs.Calculation;

@Stateless
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CalculationService {

	@PersistenceContext(unitName="hello")
	private EntityManager entityManager;
	
	@POST
	@Path("calc")
	public Response CreateCalculation (Calculation c) {
		try {
			entityManager.persist(c);
			return Response.status(Response.Status.OK)
		               .entity("{\"Result\": " + c.getResult() + "}")
		               .build();
		}
		catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
		               .entity("An error occurred: " + e.getMessage())
		               .build();
		}
	}
	
	@GET
	@Path("calculations")
	public Response GetCalculation() {
		try {
			TypedQuery<Calculation> query = entityManager.createQuery("SELECT c FROM Calculation c", Calculation.class);
			List<Calculation> calculations = query.getResultList();
			return Response.status(Response.Status.OK)
						.entity(calculations)
						.build();
		}
		catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
		               .entity("An error occurred: " + e.getMessage())
		               .build();
		}
	}	
}
