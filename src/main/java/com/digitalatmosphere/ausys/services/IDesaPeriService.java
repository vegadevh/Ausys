package com.digitalatmosphere.ausys.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.digitalatmosphere.ausys.domains.DesaPeri;
import com.digitalatmosphere.ausys.dto.CantidadCasosDTO;
import com.digitalatmosphere.ausys.dto.CasosDTO;
import com.digitalatmosphere.ausys.dto.DesaparecidoDTO;
import com.digitalatmosphere.ausys.dto.HombresMujeresDTO;
import com.digitalatmosphere.ausys.dto.PeritajeDTO;
import com.digitalatmosphere.ausys.dto.RegistroDTO;

public interface IDesaPeriService {

public List<DesaPeri> findALL() throws DataAccessException;
	
	public void save(DesaPeri desaPeri) throws DataAccessException;
	
	public void delete(Integer id_desaperi) throws DataAccessException;
	
	public Integer saveR(DesaPeri desaPeri) throws DataAccessException;
	
	public DesaPeri findOne(Integer id_desaperi) throws DataAccessException;
	
	public List<DesaPeri> findAll() throws DataAccessException;
	
	public List<PeritajeDTO> findAllPeritajes() throws DataAccessException;
	
	public List<DesaparecidoDTO> findAllDesaparecidos() throws DataAccessException;
	
	public List<PeritajeDTO> buscarNombrePeritaje(String nombre) throws DataAccessException;

	public List<PeritajeDTO> buscarIdPeritaje(String id) throws DataAccessException;
	
	public List<DesaparecidoDTO> buscarNombreDesaparecido(String nombre) throws DataAccessException;
	
	public List<DesaparecidoDTO> buscarIdDesaparecido(String id) throws DataAccessException;
	
	public List<RegistroDTO> verRegistroPeritaje(String id_peritaje, String id_desaperi) throws DataAccessException;
	
	public List<RegistroDTO> verRegistroDesaparecido(String id_desaparecido, String id_desaperi) throws DataAccessException;
	public List<DesaPeri> findByKeyword(String keyword, String sexo) throws DataAccessException;
	
	public List<DesaPeri> findByKeywordAndtipe(String keyword, String type, String sexo) throws DataAccessException;
	
	public List<HombresMujeresDTO> HombresMujeresPorFecha(String inicio, String fin) throws DataAccessException;
	
	public List<CasosDTO> cantidadPorCasos() throws DataAccessException;
	
	public List<CasosDTO> cantidadPorCasosRango(String inicio, String fin) throws DataAccessException;
	
	public List<HombresMujeresDTO> HombresMujeresPorCaso(String caso) throws DataAccessException;
	
	public List<HombresMujeresDTO> HombresMujeresPorCasoYRango(String caso, String inicio, String fin) throws DataAccessException;
	
	public List<CasosDTO> cantidadPorCasosSexo(String sexo) throws DataAccessException;
	
	public List<DesaparecidoDTO> PeritajesXDesaparecidos(String nombre, String apellido) throws DataAccessException;
}
