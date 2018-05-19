declare function local:isValidTicket($id as xs:string)
   {
let $today := current-date()
for $patron in doc("/Users/jackcollins/Desktop/xml/readerticket.xml")/readerTickets/readerTicket
let $s := $patron/@id
where $s = $id
let $date:= $patron//readerTicket.expiryDate
let $patronfn := $patron/readerTicket.name/readerTicket.firstName
let $patronsn := $patron/readerTicket.name/readerTicket.surname

return if (xs:date($today) < xs:date("2020-12-03"))
       then <result>{$patronfn/text()}{" "} {$patronsn/text()} {"'s The ticket is valid"} </result>
                 else <result>{$patronfn/text()}{" "} {$patronsn/text()} {"'sThe ticket is invalid"}</result>
};

local:isValidTicket("001")