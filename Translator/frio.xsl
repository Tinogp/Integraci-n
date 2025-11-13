<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    
    <xsl:template match="/">
        <peticion_fria>
            <id_ticket>
                <xsl:value-of select="//id"/>
            </id_ticket>
            
            <bebida>
                <xsl:value-of select="//name"/>
            </bebida>
            
            <area>BARRA_FRIOS</area>
        </peticion_fria>
    </xsl:template>
    
</xsl:stylesheet>