package nl.martijn1279.twdata.error

import org.springframework.http.HttpStatus

enum class ErrorCode(val statusCode: HttpStatus, val description: String, val isValidationError: Boolean) {
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An unknown error has occurred.", false),

    INVALID_FORMAT(HttpStatus.BAD_REQUEST, "The received message has an invalid format.", true),
    INVALID_FIELD(HttpStatus.BAD_REQUEST, "The received message contains an invalid field.", true),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "An error occurred while validating the input.", true),

    NON_FOUND(HttpStatus.NOT_FOUND, "No information was found for the request.", false),


//    NO_COORDS(11, "Geen coordinaten ontvangen!", true),
//    NO_UNITS(12, "Geen eenheden bekend!", true),
//    DUMP_OWN_VILLAGE(13, "Dump op eigen dorp.", true),
//    INVALID_TROOPS_INFO(14, "Troepeninfo onjuist!", true),
//    INVALID_DATE(15, "Ongeldige date!", true),
//    INVALID_WORLD(16, "Ongeldige Wereld!", true),
//    INVALID_VERSION(17, "Ongeldige Versie!", true),
//    INVALID_VILLAGE(18, "Ongeldige village!", true),
//    ALREADY_UPLOADED(19,"Dit rapport is al geupload!",false),
//    NEWER_REPORT_EXISTS(20,"Recenter rapport aanwezig!",false),
//
//    NO_INFO_FOUND(21, "Er is geen info gevonden voor het opgegeven dorp", false),
//
//    NOT_IMPLEMENTED_YET(22,"Deze functionaliteit is nog in ontwikkeling", false),
//
//    ALREADY_EXISTS(19,"Deze info is al opgeslagen",false),
//    NO_GROUPS(23,"Er zijn geen groepen beschikbaar", true),
//    GROUP_DOESNT_EXISTS(23,"De gegeven groep bestaat niet", true),
//    NOTHING_DELETED(24, "Er is niks verwijderd",false),
//
//    FIRST_LOGIN(25,"Eerste keer inlog",false)
}
