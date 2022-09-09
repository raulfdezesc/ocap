function validarFormulario(formu, validarRadioProcedimiento){
      // PRIMER APELLIDO
   if (validarRadioProcedimiento == '') {      
      var cadena = formu.DApellido1.value;
      if(cadena=='') {
         alert('Debe rellenar el campo \'Apellidos\'.');
         return false;
      }else{
         cadena = trim(cadena);
         if(cadena != null && cadena =='') {
            alert('Debe rellenar el campo \'Apellidos\'.');
            return false;
         }else if(cadena != null && cadena !='') {
            if(LetrasYNumeros(formu.DApellido1)){
               formu.DApellido1.value = cadena;
            } else{
               alert('El campo \'Apellidos\' contiene caracteres no permitidos.');
               return false;
            }
         }
      }
   }else{
      var cadena = formu.DApellido1.value;
      if(cadena=='') {
         alert('Debe rellenar el campo \'Primer Apellido\'.');
         return false;
      }else{
         cadena = trim(cadena);
         if(cadena != null && cadena =='') {
            alert('Debe rellenar el campo \'Primer Apellido\'.');
            return false;
         }else if(cadena != null && cadena !='') {
            if(LetrasYNumeros(formu.DApellido1)){
               formu.DApellido1.value = cadena;
            } else{
               alert('El campo \'Primer Apellido\' contiene caracteres no permitidos.');
               return false;
            }
         }
      }
      
      var cadena = formu.DApellido2.value;
      if(cadena=='') {
         alert('Debe rellenar el campo \'Segundo Apellido\'.');
         return false;
      }else{
         cadena = trim(cadena);
         if(cadena != null && cadena =='') {
            alert('Debe rellenar el campo \'Segundo Apellido\'.');
            return false;
         }else if(cadena != null && cadena !='') {
            if(LetrasYNumeros(formu.DApellido2)){
               formu.DApellido2.value = cadena;
            } else{
               alert('El campo \'Segundo Apellido\' contiene caracteres no permitidos.');
               return false;
            }
         }
      }       
   }
   
   // NOMBRE
   var cadena = formu.DNombre.value;
   if(cadena=='') {
      alert('Debe rellenar el campo \'Nombre\'.');
      return false;
   }else{
      cadena = trim(cadena);
      if(cadena=='') {
         alert('Debe rellenar el campo \'Nombre\'.');
         return false;
      }else{
         if(LetrasYNumeros(formu.DNombre)){
            formu.DNombre.value = cadena;
         } else{
            alert('El campo \'Nombre\' contiene caracteres no permitidos.');
            return false;
         }
      }
   }

   // NIF/NIE
   var cadena = formu.CDniReal.value;
   if(cadena=='') {
      alert('Debe rellenar el campo \'NIF/NIE\'.');
      return false;
   }else{
      cadena = trim(cadena);
      if(cadena=='') {
         alert('Debe rellenar el campo \'NIF/NIE\'.');
         return false;
      }else{
         if(validar_nif(cadena) != 0){
            formu.CDniReal.value = cadena;
         } else{
            alert('El campo \'NIF/NIE\' es erróneo.');
            return false;
         }
      }
   }
   
   // SEXO
   if(formu.BSexo.value == ''){
      alert('Debe rellenar el campo \'Sexo\'.');
      return false;
   }   

   // TELÉFONO 1
   var cadena = formu.NTelefono1.value;
   if(cadena=='') {
      alert('Debe rellenar el campo \'Tel\u00e9fono 1\'.');
      return false;
   } else {
	   cadena = trim(cadena);
	   if(cadena!='') {
	         if(telefonos(formu.NTelefono1)){
	            formu.NTelefono1.value = cadena;
	         } else{
	            alert('El campo \'Tel\u00e9fono 1\' tiene un formato err\u00f3neo. Debe ser num\u00e9rico de 9 d\u00edgitos.');
	            return false;
	         }
	   }
   }

   // TELÉFONO 2
   var cadena = formu.NTelefono2.value;
   cadena = trim(cadena);
   if(cadena!='') {
         if(telefonos(formu.NTelefono2)){
            formu.NTelefono2.value = cadena;
         } else{
            alert('El campo \'Tel\u00e9fono 2\' tiene un formato err\u00f3neo. Debe ser num\u00e9rico de 9 d\u00edgitos.');
            return false;
         }
   }

   // CORREO ELECTRÓNICO
   var cadena = formu.DCorreoelectronico.value;
   if(cadena=='') {
      alert('Debe rellenar el campo \'Correo Electr\u00f3nico\'.');
      return false;
   } else {
	   var cadena = formu.DCorreoelectronico.value;
	   cadena = trim(cadena);
	      
	   if (cadena != '') {
	            if(esDireccionCorreo(formu.DCorreoelectronico)){
	               formu.DCorreoelectronico.value = cadena;
	            } else{
	               alert('El campo \'Correo Electr\u00f3nico\' tiene un formato err\u00f3neo.');
	               return false;
	            }
	   }
   }

   // CODIGO POSTAL
   var cadena = formu.CPostalUsu.value;
   cadena = trim(cadena);
   if (cadena != '') {
      if (cadena.length  < 4 || cadena.length > 5 ) {
         alert('El campo \'C\u00f3digo Postal\' tiene un formato err\u00f3neo. Deben estar formado por 5 d\u00edgitos.');
         return false;
      } else {
         if(soloNumeros(formu.CPostalUsu)){
            if (cadena.length==4)
               cadena = '0'+cadena;
               if (formu.CProvinciaUsu_id.value !='' && formu.CProvinciaUsu_id.value != cadena.substring(0,2)) {
                  alert('El campo \'C\u00f3digo Postal\' no pertenece a la provincia seleccionada.');
                  return false;
               }
            formu.CPostalUsu.value = cadena;
         } else{
            alert('El campo \'C\u00f3digo Postal\' tiene un formato err\u00f3neo. Debe estar formado por 5 d\u00edgitos.');
            return false;
         }
      }
   }

   // Regimen Juridico
   if(!formu.CJuridico[0].checked && !formu.CJuridico[1].checked && !formu.CJuridico[2].checked){
      alert('Debe rellenar el campo \'R\u00e9gimen jur\u00eddico\'.');
      return false;
   }

  // Situacion Administrativa auxiliar
   if(!formu.CSitAdministrativaAuxId[0].checked && !formu.CSitAdministrativaAuxId[1].checked){
      alert('Debe rellenar el campo \'Situaci\u00f3n Administrativa\'.');
      return false;
   }
   if(formu.CSitAdministrativaAuxId[1].checked && formu.DSitAdministrativaAuxOtros.value==''){
      alert('Debe rellenar el campo \'\Otras situaciones administrativas\'.');
      return false;
   }

   // ESTATUTARIO
   if(formu.CEstatutarioActualId.value == '') {
      alert('Debe rellenar el campo \'Personal\' en sus Datos Profesionales Actuales.');
      return false;
   }

   // CATEGORÍA PROFESIONAL
   if(formu.CProfesionalActual_id.value == ''){
      alert('Debe rellenar el campo \'Categor\u00EDa\' en sus Datos Profesionales Actuales.');
      return false;
   }
   // ESPECIALIDAD
   if(formu.CEspecActual_id != null){
      if(formu.CEspecActual_id.value == 'Seleccione'){
         alert('Debe rellenar el campo \'Especialidad\' en sus Datos Profesionales Actuales.');
         return false;
      }
   }
   // PROVINCIA
   if(formu.CProvincia_id.value == ''){
      alert('Debe rellenar el campo \'Provincia\'.');
      return false;
   }
   // TIPO GERENCIA
   if(formu.CTipogerencia_id.value == ''){
      alert('Debe rellenar el campo \'Tipo de Gerencia\'.');
      return false;
   }
   // GERENCIA
   if (validarRadioProcedimiento == '') {
      if(formu.CGerencia_id.value == ''){
         alert('Debe rellenar el campo \'Gerencia\'.');
         return false;
      }
   }else{
      if(formu.CGerencia_id!= null){
         if(formu.CGerencia_id.value == ''){
            alert('Debe rellenar el campo \'Gerencia\'.');
            return false;
         }
      }else{
         alert('Dato err\u00f3neo. Gerencia inexistente para esa Provincia y Tipo de Gerencia');
         return false;      
      }
   }
   // CENTRO DE TRABAJO
   if (validarRadioProcedimiento == '') {
      if(formu.CCentrotrabajo_id.value == ''){
         alert('Debe rellenar el campo \'Centro de Trabajo\'.');
         return false;
      }
   }else{
      if(formu.CCentrotrabajo_id!= null){
         if(formu.CCentrotrabajo_id.value == ''){
            alert('Debe rellenar el campo \'Centro de Trabajo\'.');
            return false;
         }
      }else{
         alert('Dato err\u00f3neo. Centro inexistente para esa Gerencia');
         return false;      
      }
   }
   
   // Situacion Administrativa
   if (validarRadioProcedimiento == '') {
      if(formu.CSitAdministrativaId.value == ''){
         alert('Debe rellenar el campo \'Procedimiento de evaluaci\u00f3n por el que opta\'.');
         return false;
      }
   } else {
      if(!formu.CProcedimiento[0].checked && !formu.CProcedimiento[1].checked && !formu.CProcedimiento[2].checked && !formu.CProcedimiento[3].checked && !formu.CProcedimiento[4].checked){
         alert('Debe rellenar el campo \'Procedimiento de evaluaci\u00f3n por el que opta\'.');
         return false;
      }
   }

   // Situacion Administrativa
   if (validarRadioProcedimiento == '') {
      if(formu.CSitAdministrativaId.value == ''){
         alert('Debe rellenar el campo \'Procedimiento de evaluaci\u00f3n por el que opta\'.');
         return false;
      }
   } else {
      if(!formu.CProcedimiento[0].checked && !formu.CProcedimiento[1].checked && !formu.CProcedimiento[2].checked && !formu.CProcedimiento[3].checked && !formu.CProcedimiento[4].checked){
         alert('Debe rellenar el campo \'Procedimiento de evaluaci\u00f3n por el que opta\'.');
         return false;
      }
   }
   
   // GRADO
   if(formu.CGrado_id != null){
      if(formu.CGrado_id.value == ''){
         alert('Debe rellenar el campo \'Grado\'.');
         return false;
      }
   }
   
   // ESTATUTARIO
   if(formu.CEstatutarioId.value == '') {
      alert('Debe rellenar el campo \'Personal\' en los datos por los que opta a Carrera Profesional.');
      return false;
   }

   // CATEGORÍA PROFESIONAL
   if(formu.CProfesional_id.value == ''){
      alert('Debe rellenar el campo \'Categor\u00EDa\' en los datos por los que opta a Carrera Profesional.');
      return false;
   }
   // ESPECIALIDAD
   if(formu.CEspec_id != null){
      if(formu.CEspec_id.value == 'Seleccione'){
         alert('Debe rellenar el campo \'Especialidad\' en los datos por los que opta a Carrera Profesional.');
         return false;
      }
   }
   
   if (validarRadioProcedimiento=='') {
      //FECHA REGISTRO OFICIAL
      var cadena = formu.FRegistro_oficial.value;
      cadena = trim(cadena);
      if(cadena == ''){
         alert("Debe rellenar el campo \" Fecha de Registro Oficial \".");
         return false;
      }
      //Si no introduce la hora, ponemos por defecto las 00:00:00
      if (cadena.length < 19 && cadena.length >= 10) {
         cadena = cadena.substring(0,10)+' 00:00:00';
         formu.FRegistro_oficial.value = cadena;
      }
      if (!(esTimestampCorrecto(cadena))){
         alert("Formato de \" Fecha de Registro Oficial \" no es correcto.");
         return false;
      }
      if(comprobarFechaNoFutura(cadena)){
         alert("La \" Fecha de Registro Oficial \" no es correcta.");
         return false;
      } 
      if(esFechaHora1MayorQueFechaHora2(cadena, cadena.substr(11,8), formu.FRegistro_solic.value, formu.FRegistro_solic.value.substr(11,8))){
         alert("La \" Fecha de Registro Oficial \" no es correcta.");
         return false;
      }
   }
   // FECHA DE REALIZACIÓN Y ENVÍO 
//   var cadena = formu.FRegistro_solic.value;
//   cadena = trim(cadena);
//   if(cadena=='') {
//      alert('Debe rellenar el campo \'Fecha de realización y envio\'.');
//      return false;
//   }else{
//      cadena = trim(cadena);
//      if(cadena=='') {
//         alert('Debe rellenar el campo \'Fecha de realización y envio\'.');
//         return false;
//      }else{
//         if(esFechaCorrecta(cadena)){
//            formu.FRegistro_solic.value = cadena;
//         } else{
//            alert('El campo \'Fecha de realización y envio\' no es una fecha.');
//            return false;
//         }
//      }
//   }
   
      // AÑOS ANTIGUEDAD
   var cadena = formu.NAniosantiguedad.value;
   cadena = trim(cadena);
   if(cadena=='') {
      alert('Debe rellenar el campo \'N\u00famero de a\u00f1os\'.');
      return false;
   }else{
      cadena = trim(cadena);
      if(cadena=='') {
         alert('Debe rellenar el campo \'N\u00famero de a\u00f1os\'.');
         return false;
      }
      else {
         if(!soloNumeros(document.forms[0].NAniosantiguedad)){
            alert('El campo \'N\u00famero de a\u00f1os\' es err\u00f3neo.');
            return false;
         }
      }
   }
   // MESES ANTIGUEDAD
   var cadena = formu.NMesesantiguedad.value;
   cadena = trim(cadena);
/*
   if(cadena=='') {
      alert('Debe rellenar el campo \'N\u00famero de meses\'.');
      return false;
   }else{
      cadena = trim(cadena);
      if(cadena=='') {
         alert('Debe rellenar el campo \'N\u00famero de meses\'.');
         return false;
      }else {
*/
if(cadena=='') {
 formu.NMesesantiguedad.value = '0';
 cadena = '0';
}
         if(!soloNumeros(formu.NMesesantiguedad)){
            alert('El campo \'N\u00famero de meses\' es err\u00f3neo.');
            return false;
         } else if (parseInt(cadena) > 11) {
            alert('El campo \'N\u00famero de meses\' no puede ser mayor o igual a 12.');
            return false;
         }
/*
      }
   }
*/
   // DIAS ANTIGUEDAD
   var cadena = formu.NDiasantiguedad.value;
   cadena = trim(cadena);
/*
   if(cadena=='') {
      alert('Debe rellenar el campo \'N\u00famero de d\u00edas\'.');
      return false;
   }else{
      cadena = trim(cadena);
      if(cadena=='') {
         alert('Debe rellenar el campo \'N\u00famero de d\u00edas\'.');
         return false;
      }else {
*/
if(cadena=='') {
 formu.NDiasantiguedad.value = '0';
 cadena = '0';
}
         if(!soloNumeros(formu.NDiasantiguedad)){
            alert('El campo \'N\u00famero de d\u00edas\' es err\u00f3neo.');
            return false;
         } else if (parseInt(cadena) > 30) {
            alert('El campo \'N\u00famero de d\u00edas\' no puede ser mayor que 30.');
            return false;
         }
   /* //21-8-09: La antiguedad total si puede ser 0    
   if (formu.NAniosantiguedad.value == '0' && formu.NMesesantiguedad.value == '0' && formu.NDiasantiguedad.value == '0') {
      alert('El tiempo total de ejercicio en la categor\u00EDa profesional por la que se accede no puede ser cero.');
      return false;
   }
   */
/*
      }
   }
*/
/*
   // PLAZA EN PROPIEDAD
   var cadena = formu.FPlazapropiedad.value;
   cadena = trim(cadena);
   if(cadena=='') {
      alert('Debe rellenar el campo \'Fecha en la que obtuvo la plaza en propiedad\'.');
      return false;
   }else{
      cadena = trim(cadena);
      if(cadena=='') {
         alert('Debe rellenar el campo \'Fecha en la que obtuvo la plaza en propiedad\'.');
         return false;
      }else{
         if(esFechaCorrecta(cadena)){
            formu.FPlazapropiedad.value = cadena;
         } else{
            alert('El campo \'Fecha en la que obtuvo la plaza en propiedad\' no es una fecha.');
            return false;
         }
      }
   }  

  // Otros servicios
   if(!formu.BOtroServicio[0].checked && !formu.BOtroServicio[1].checked){
      alert('Debe rellenar el campo \'Tiene servicios prestados en otro Servicio de Salud distinto al de Castilla y Le\u00f3n\'.');
      return false;
   }
   if(formu.BOtroServicio[0].checked && formu.ADondeServicio.value==''){
      alert('Debe rellenar el campo \'\u00bfD\u00f3nde?\'.');
      return false;
   }
*/
   return true;
}
