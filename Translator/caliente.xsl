<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    
    <xsl:template match="/">
        <orden_barista>
            <codigo_pedido>
                <xsl:value-of select="//id"/>
            </codigo_pedido>
            
            <producto>
                <xsl:value-of select="//name"/>
            </producto>
            
            <instruccion>SERVIR_CALIENTE</instruccion>
        </orden_barista>
    </xsl:template>
    
</xsl:stylesheet>