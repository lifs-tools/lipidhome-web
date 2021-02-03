package uk.ac.ebi.lipidhome.service.impl;

import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.ebi.lipidhome.core.dao.IsomerDao;
import uk.ac.ebi.lipidhome.core.model.Isomer;
import uk.ac.ebi.lipidhome.service.IsomerService;
import uk.ac.ebi.lipidhome.service.result.Result;
import uk.ac.ebi.lipidhome.service.result.model.IsomerSummary;

import javax.ws.rs.core.Response;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class IsomerServiceImpl extends LipidService implements IsomerService{

    @Override
    public Response getIsomerSummary(Long id) {
        Result result;
        IsomerDao<Isomer> isomerDao = getDaoFactory().getIsomerDao();

        try {
            Isomer isomer = isomerDao.getIsomer(id);
            IsomerSummary isomerSummary = new IsomerSummary(isomer);
            isomerSummary.setXrefs(isomerDao.getCrossReferencesList(id));
            isomerSummary.setPapers(isomerDao.getPapersList(id));
            result = new Result(isomerSummary);
        }catch (RuntimeException e) {
            String errorMessage = "Record with id " + id + " is unavailable.";
            Logger.getLogger(CategoryServiceImpl.class.getSimpleName()).log(Level.SEVERE, errorMessage, e);
            result = new Result(errorMessage);
        }

        return result2Response(result);
    }
}
